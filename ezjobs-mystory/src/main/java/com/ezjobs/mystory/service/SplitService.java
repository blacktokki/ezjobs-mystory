package com.ezjobs.mystory.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Sentence;
import com.ezjobs.mystory.repository.SentenceRepository;

import kr.bydelta.koala.okt.SentenceSplitter;

@Service
public class SplitService {
	
	@Inject
	private SentenceRepository sentenceRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public List<List<String>> spliterResumes(List<Resume> resumes) {
		List<List<String>> resumesSplit=new ArrayList<>();
		for(Resume resume:resumes) {
			String str=(String)resume.getAnswer();
			if(str!=null)
				resumesSplit.add(spliter(str));
			else
				System.out.println("NULL!");
			System.out.println("-------------------------");
		}
		return resumesSplit;
	}
	
	public List<String> spliterAnswer(String answer) {
		//System.out.println(answer);
		return spliter(answer.replaceAll("(<p>|</p>)", "").replaceAll("\n", "<br>\n"));
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
		/*
		for(String s:paragraph)
			System.out.println(s);*/
		return paragraph;
	}
	
	public void sentenceAddAll(List<Resume> resumes) {
		List<List<String>> resumesSplit=spliterResumes(resumes);
		int mx=0;//,j=0;
		for(List<String> resumeSplit:resumesSplit) {
			int i=0;
			List<Sentence> sentences=new ArrayList<Sentence>();
			//String tags=resumes.get(j++).getTags();
			for(String str:resumeSplit) {
				Sentence sentence=new Sentence();
				sentence.setUserId("_admin");
				//sentence.setTags(tags);
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
			sentenceRepository.saveAll(sentences);
			sentences.clear();
		}
	}
	

	public List<String> changeSynonym(String answer) {
		List<String> sentences=spliterAnswer(answer);
		List<String> sentences2=new ArrayList<>();
		for(String sentence:sentences) {
			
			List<?> synonyms=entityManager
		        .createNativeQuery("SELECT distinct keyword , synonym FROM synonym "
		        		+ "where match(keyword) against('"+sentence+"') ORDER BY LENGTH(keyword) DESC")
		        .getResultList();
			sentence="<ul class='p-0'><li class='d-inline-block align-middle'>"+sentence.replaceAll(" ","&nbsp;</li><li class='d-inline-block align-middle'>")+"&nbsp;</li></ul>";
			//System.out.println(sentence);
			Map<String,String> mapStr=new HashMap<>();
			for(Object obj:synonyms) {
				Object[] strs=(Object[])obj;
				String keyword=(String)strs[0];
				String synonym=(String)strs[1];
				if(mapStr.get(keyword)==null) {
					mapStr.put(keyword,"<select class='form-control p-0'><option value="+keyword+">"+keyword+"</option>");
				}
				mapStr.put(keyword,mapStr.get(keyword)+"<option value="+synonym+">"+synonym+"</option>");
			}
			int i=0;
			for(Map.Entry<String, String> elem : mapStr.entrySet() ) {
				sentence=sentence.replaceAll(elem.getKey(),"<"+i+">");
				i++;
			}
			i=0;
			for(Map.Entry<String, String> elem : mapStr.entrySet() ) {
				sentence=sentence.replaceAll("<"+i+">",elem.getValue()+"<option value='_add'>추가..</option></select>");
				i++;
			}
			sentences2.add(sentence);
		}
		//System.out.println(sentences.get(2));
		return sentences2;
	}
}
