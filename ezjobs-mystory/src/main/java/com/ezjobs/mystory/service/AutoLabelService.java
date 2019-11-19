package com.ezjobs.mystory.service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.elasticsearch.script.Script;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.ScriptField;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.data.elasticsearch.core.query.SourceFilter;
//import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.iterator.StringArrayIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.IDSorter;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelSequence;
import kr.bydelta.koala.okt.SentenceSplitter;

import com.ezjobs.mystory.dto.ElasticResume;
import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Sentence;
import com.ezjobs.mystory.repository.SentenceRepository;
import com.ezjobs.mystory.repository.SynonymRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class AutoLabelService {
	
	@Inject
	private ElasticsearchOperations elasticsearchTemplate;
	
	@Inject
	private SynonymRepository synonymRepository;
	
	@Inject
	private SentenceRepository sentenceRepository;
	
	@Inject
	private ObjectMapper mapper;
	
	public void temp() {
		//String[] array={"aaa bbb ccc","bbb ccc ddd","aaa ddd eee","aaa ccc","bbb ccc"};
		Script script=new Script("List a=new ArrayList();"
							   + "for(t in doc['answer'].values){"
							   + "	if(t.length()==2)"
							   + "		a.add(t);"
							   + "}"
							   + "return a;"
							   );
		Script script2=new Script("doc['question.keyword']");
		//SourceFilter sourceFilter = new FetchSourceFilter(new String[]{"question"}, null);
		PageRequest pr=PageRequest.of(0, 32768);
		SearchQuery searchQuery=new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery())//queryStringQuery("*wonwoo*")
				.withIndices("intro_mnvv")
      			//.withTypes("doc")
				//.withSourceFilter(sourceFilter)
				.withScriptField(new ScriptField("terms",script))
				.withScriptField(new ScriptField("question",script2))
				.withPageable(pr)
				.build();	
		Page<ElasticResume> page=elasticsearchTemplate.queryForPage(searchQuery,ElasticResume.class);
		
		ArrayList<String> al=new ArrayList<>();
		for(ElasticResume resume:page.getContent()) {
			String[] strs=resume.getTerms();
			String str;
			if(strs!=null) {
				str=Arrays.toString(strs).replaceAll("[\\,\\[\\]]", "");
			}
			else {
				str="미분류";
			}
			al.add(str);
			//System.out.println(resume.getQuestion());
			//System.out.println(str);
		}
		temp(al.toArray(new String[al.size()]));
	}
	private void temp(String[] array) {
		int numTopic=450;//maximum300
		Pattern pattern = Pattern.compile("[\\p{L}\\p{N}_]+");
		ArrayList<Pipe> pipelist=new ArrayList<Pipe>();
		pipelist.add(new Input2CharSequence("UTF-8"));
		pipelist.add(new CharSequence2TokenSequence(pattern));
		pipelist.add(new TokenSequence2FeatureSequence());
		InstanceList instances=new InstanceList(new SerialPipes(pipelist));
		instances.addThruPipe(new StringArrayIterator(array));
		ParallelTopicModel model=new ParallelTopicModel(numTopic,1.0,1.0/numTopic);
		model.addInstances(instances);
		model.setNumThreads(4);
		model.setNumIterations(4500);

		try {
			model.estimate();
			ArrayList<TreeSet<IDSorter>> topicSortedWords=model.getSortedWords();
			Alphabet dataAlphabet=model.getAlphabet();
			
			Formatter out;
			for(int i=0;i<array.length;i++) {
				out=new Formatter(new StringBuilder(), Locale.US);
				FeatureSequence tokens=(FeatureSequence) model.data.get(i).instance.getData();
				LabelSequence topics = model.getData().get(i).topicSequence;
				System.out.println(i+"번째 문서");
		        for (int position = 0; position < tokens.getLength(); position++) {
		            out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
		        }
		        System.out.println(out);
		        double[] topicDistribution = model.getTopicProbabilities(i);
		        for (int topic = 0; topic < numTopic; topic++) {
		            if(topicDistribution[topic]>1.0/numTopic){        
		            	out = new Formatter(new StringBuilder(), Locale.US);
		            	out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
		            	System.out.println(out);
		            }
		        }
			}
			
			
			for(int i=0;i<topicSortedWords.size();i++) {
				Iterator<IDSorter> iterator=topicSortedWords.get(i).iterator();
				System.out.println(i+"번째 카테고리");
				out = new Formatter(new StringBuilder(), Locale.US);
				int rank = 0;
				while (iterator.hasNext() && rank < 5) {
	                IDSorter idCountPair = iterator.next();
	                out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
	                rank++;
	            }
				System.out.println(out);
			}
			for(int i=0;i<topicSortedWords.size();i++) {
				Iterator<IDSorter> iterator=topicSortedWords.get(i).iterator();
				int rank = 0,w=0;
				while (iterator.hasNext() && rank < 5) {
	                IDSorter idCountPair = iterator.next();
	                System.out.print(dataAlphabet.lookupObject(idCountPair.getID())+" ");
	                if(rank==2)
	                	w=(int)idCountPair.getWeight();
	                rank++;
	            }
				System.out.println(w);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void spliterResumes(Model model) {
		Map<String,Object> modelMap=model.asMap();
		Object resumesObj=mapper.convertValue(modelMap.get("resumes"),Map.class).get("content");
		List<Resume> resumes=mapper.convertValue(resumesObj,new TypeReference<List<Resume>>(){});
		List<List<String>> resumesSplit=new ArrayList<>();
		for(Resume resume:resumes) {
			String str=(String)resume.getAnswer();
			if(str!=null)
				resumesSplit.add(spliter(str));
			else
				System.out.println("NULL!");
			System.out.println("-------------------------");
		}
		model.addAttribute("resumesSplit",resumesSplit);
	}
	
	public void spliterAnswer(Model model) {
		Map<String,Object> modelMap=model.asMap();
		String answer=(String)modelMap.get("answer");
		System.out.println(answer);
		List<String> strs = spliter(answer);
		/*for(String str:strs){
			
		}*/
		model.addAttribute("sentences",strs);
	}
	
	private List<String> spliter(String str) {
		SentenceSplitter splitter = new SentenceSplitter();
		List<String> paragraph = splitter.sentences(str.replaceAll("다\\.", "다. "));
		return spliterAddon(paragraph);
	}
	
	private List<String> spliterAddon(List<String> strs) {
		List<String> paragraph = new ArrayList<String>();
		for(String s:strs) {
			 Collections.addAll(paragraph,(s.split("\n")));
		}
		for(String s:paragraph)
			System.out.println(s);
		return paragraph;
	}
	
	public void sentenceAddAll(Model model) {
		Map<String,Object> modelMap=model.asMap();
		List<List<String>> resumesSplit=mapper.convertValue(modelMap.get("resumesSplit"),new TypeReference<List<List<String>>>(){});	
		List<Sentence> sentences=new ArrayList<Sentence>();
		int mx=0;
		for(List<String> resumeSplit:resumesSplit) {
			int i=0;
			for(String str:resumeSplit) {
				Sentence sentence=new Sentence();
				sentence.setUserId("_admin");
				sentence.setText(str);
				sentence.setPosition(i++);
				sentence.setPositionMax(resumeSplit.size());
				if(mx<str.length()) {
					mx=str.length();
					System.out.println(str);
				}
				System.out.println(mx);
				sentences.add(sentence);
			}
		}
		sentenceRepository.saveAll(sentences);
	}
}
