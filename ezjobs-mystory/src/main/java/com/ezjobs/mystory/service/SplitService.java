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
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Sentence;
import com.ezjobs.mystory.repository.SentenceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.bydelta.koala.okt.SentenceSplitter;

@Service
public class SplitService {
	
	@Inject
	private SentenceRepository sentenceRepository;
	
	@Inject
	private ObjectMapper mapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
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
		String answer=((String)modelMap.get("answer")).replaceAll("\n", "<br>\n");
		//System.out.println(answer);
		List<String> strs = spliter(answer);
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
		/*
		for(String s:paragraph)
			System.out.println(s);*/
		return paragraph;
	}
	
	public void sentenceAddAll(Model model) {
		Map<String,Object> modelMap=model.asMap();
		Object resumesObj=mapper.convertValue(modelMap.get("resumes"),Map.class).get("content");
		List<Resume> resumes=mapper.convertValue(resumesObj,new TypeReference<List<Resume>>(){});
		List<List<String>> resumesSplit=mapper.convertValue(modelMap.get("resumesSplit"),new TypeReference<List<List<String>>>(){});	
		int mx=0,j=0;
		for(List<String> resumeSplit:resumesSplit) {
			int i=0;
			List<Sentence> sentences=new ArrayList<Sentence>();
			String tags=resumes.get(j++).getTags();
			for(String str:resumeSplit) {
				Sentence sentence=new Sentence();
				sentence.setUserId("_admin");
				sentence.setTags(tags);
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
	

	public void changeSynonym(Model model) {
		List<String> sentences=mapper.convertValue(model.getAttribute("sentences"),new TypeReference<List<String>>(){});
		List<String> sentences2=new ArrayList<>();
		for(String sentence:sentences) {
			String checksum="";
			List<?> synonyms=entityManager
		        .createNativeQuery("SELECT distinct keyword , synonym FROM Synonym "
		        		+ "where match(keyword) against('"+sentence+"')")
		        .getResultList();
			Map<String,String> mapStr=new HashMap<>();
			for(Object obj:synonyms) {
				Object[] strs=(Object[])obj;
				String keyword=(String)strs[0];
				String synonym=(String)strs[1];
				if(mapStr.get(keyword)==null) {
					if(checksum.contains(keyword)) {
						continue;
					}
					checksum+=keyword+" ";
					mapStr.put(keyword,"<select class='form-control p-0'><option value="+keyword+">"+keyword+"</option>");
				}
				mapStr.put(keyword,mapStr.get(keyword)+"<option value="+synonym+">"+synonym+"</option>");
			}
			for(Map.Entry<String, String> elem : mapStr.entrySet() ) {
				/*System.out.println(elem.getKey());
				System.out.println(elem.getValue());*/
				sentence=sentence.replaceAll(elem.getKey(),elem.getValue()+"</select>");
			}
			sentences2.add(sentence);
		}
		//System.out.println(sentences.get(2));
		model.addAttribute("sentences",sentences2);
	}
}
