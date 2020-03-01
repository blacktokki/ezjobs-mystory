package com.ezjobs.mystory.service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.elasticsearch.script.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.ScriptField;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.data.elasticsearch.core.query.SourceFilter;
//import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Service;

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

import com.ezjobs.mystory.dto.ElasticResume;
import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Tag;
import com.ezjobs.mystory.repository.ResumeRepository;
import com.ezjobs.mystory.repository.TagRepository;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class KeywordAnalysisService {
	
	static final Logger logger = LoggerFactory.getLogger(KeywordAnalysisService.class);
	
	@Inject
	private TagRepository tagRepository;
	
	@Inject
	private ResumeRepository ResumeRepository;
	
	@Inject
	private ElasticsearchOperations elasticsearchTemplate;
	
	public void tagger() {
		Tag tagEx=new Tag();
		tagEx.setType("유형");
		List<Tag> tags=tagRepository.findAll(Example.of(tagEx));
		List<Resume> resumes=ResumeRepository.findAll();
		Map<Integer,Integer> resumeMap=new HashMap<>();
		PageRequest pr=PageRequest.of(0,10000);
		for(int i=0;i<resumes.size();i++) {
			//resumes.get(i).setTags(resumes.get(i).getDept());
			resumeMap.put(resumes.get(i).getId(),i);
		}
		for(Tag tag:tags) {
			String name=tag.getName();
			logger.info(name);
			SearchQuery searchQuery=new NativeSearchQueryBuilder()
					.withQuery(matchQuery("question",name))
					.withIndices("intro")
					.withPageable(pr)
	      			//.withTypes("doc")
					//.withSourceFilter(sourceFilter)
					.build();
			List<ElasticResume> list=elasticsearchTemplate.queryForList(searchQuery,ElasticResume.class);
			logger.info(((Integer)list.size()).toString());
			/*
			for(ElasticResume resume:list) {
				//logger.info(resume.getId());
				//int i=resumeMap.get(resume.getId());
				//logger.info(resumes.get(i).getId());
				//resumes.get(i).setTags(name+","+resumes.get(i).getTags());
			}*/
			//logger.info(resumes.get(0).getQuestion());
			//logger.info(resumes.get(0).getTags());
		}
		/*
		for(Resume resume:resumes) {
			logger.info(resume.getTags());
		}*/
		ResumeRepository.saveAll(resumes);
	}
	
	public void taggerCount() {
		Tag tagEx=new Tag();
		tagEx.setType("유형");
		List<Tag> tags=tagRepository.findAll(Example.of(tagEx));
		List<Resume> resumes=ResumeRepository.findAll();
		Map<Integer,Integer> resumeMap=new HashMap<>();
		PageRequest pr=PageRequest.of(0,10000);
		for(int i=0;i<resumes.size();i++) {
			//resumes.get(i).setTags(resumes.get(i).getDept());
			resumeMap.put(resumes.get(i).getId(),i);
		}
		for(Tag tag:tags) {
			String name=tag.getName();
			SearchQuery searchQuery=new NativeSearchQueryBuilder()
					.withQuery(matchQuery("question",name))
					.withIndices("intro")
					.withPageable(pr)
	      			//.withTypes("doc")
					//.withSourceFilter(sourceFilter)
					.build();
			List<ElasticResume> list=elasticsearchTemplate.queryForList(searchQuery,ElasticResume.class);
			logger.info(name+":"+list.size());
		}
	}
	
	
	public void execute() {
		//String[] array={"aaa bbb ccc","bbb ccc ddd","aaa ddd eee","aaa ccc","bbb ccc"};
		Script script=new Script("List a=new ArrayList();"
							   + "for(t in doc['answer'].values){"
							   + "	if(t.length()==2)"
							   + "		a.add(t);"
							   + "}"
							   + "return a;"
							   );
		Script script2=new Script("doc['id']");
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
			//logger.info(resume.getQuestion());
			//logger.info(str);
		}
		execute(al.toArray(new String[al.size()]));
	}
	private void execute(String[] array) {
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
				logger.info(i+"번째 문서");
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
				logger.info(i+"번째 카테고리");
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
	

}
