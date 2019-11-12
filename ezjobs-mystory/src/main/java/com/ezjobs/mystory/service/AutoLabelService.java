package com.ezjobs.mystory.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class AutoLabelService {
	
	@Inject
	private ElasticsearchOperations elasticsearchTemplate;
	
	@Inject
	ObjectMapper mapper;
	
	public void temp() {
		//String[] array={"aaa bbb ccc","bbb ccc ddd","aaa ddd eee","aaa ccc","bbb ccc"};
		Script script=new Script("List a=new ArrayList();"
							   + "for(t in doc['question'].values){"
							   + "	if(t.length()>1&& a.size()<5)"
							   + "		a.add(t);"
							   + "}"
							   + "return a;"
							   );
		Script script2=new Script("doc['question.keyword']");
		//SourceFilter sourceFilter = new FetchSourceFilter(new String[]{"question"}, null);
		PageRequest pr=PageRequest.of(0, 32768);
		SearchQuery searchQuery=new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery())
				.withIndices("intro")
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
			System.out.println(resume.getQuestion());
			System.out.println(str);
		}
		temp(al.toArray(new String[al.size()]));
	}
	private void temp(String[] array) {
		int numTopic=300;//maximum300
		Pattern pattern = Pattern.compile("[\\p{L}\\p{N}_]+");
		ArrayList<Pipe> pipelist=new ArrayList<Pipe>();
		pipelist.add(new Input2CharSequence("UTF-8"));
		pipelist.add(new CharSequence2TokenSequence(pattern));
		pipelist.add(new TokenSequence2FeatureSequence());
		InstanceList instances=new InstanceList(new SerialPipes(pipelist));
		instances.addThruPipe(new StringArrayIterator(array));
		ParallelTopicModel model=new ParallelTopicModel(numTopic,0.1,1.0/numTopic);
		model.addInstances(instances);
		model.setNumThreads(4);
		model.setNumIterations(3000);

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void spliterResumes(Model model) {
		Map<String,Object> modelMap=model.asMap();
		Object resumesObj=modelMap.get("resumes");
		List<?> resumes=mapper.convertValue(resumesObj, List.class);
		List<List<String>> resumesSplit=new ArrayList<>();
		for(Object resume:resumes) {
			Object[] resumeArr=mapper.convertValue(resume,Object[].class);
			String str=(String)resumeArr[2];
			if(str!=null)
				resumesSplit.add(spliter(str));
			else
				System.out.println("NULL!");
			System.out.println("-------------------------");
		}
		model.addAttribute("resumesSplit",resumesSplit);
	}
	
	private List<String> spliter(String str) {
		SentenceSplitter splitter = new SentenceSplitter();
		List<String> paragraph = splitter.sentences(str);
		for(String s:paragraph)
			System.out.println(s);
		return paragraph;
	}
}
