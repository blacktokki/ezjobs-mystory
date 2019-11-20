package com.ezjobs.mystory.service;

import java.util.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Sentence;
import com.ezjobs.mystory.repository.ResumeRepository;
import com.ezjobs.mystory.repository.SentenceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResumeService {
	
	@Inject
	ResumeRepository resumeRepository;
	
	@Inject
	SentenceRepository sentenceRepository;
	
	@Inject
	ObjectMapper mapper;

	@PersistenceContext
	private EntityManager entityManager;
	
	public void group(Model model){
		String userId="_SI";
		String group="userId";
		List<Object[]> resumeGroup = entityManager
		        .createQuery("SELECT "+group+", COUNT("+group+") AS total FROM Resume "
		        		+ "WHERE userId=?1 GROUP BY "+group,Object[].class)
		        .setParameter(1, userId)
		        .getResultList();
		/*
		for (Object[] result : results) {
			String name = (String) result[0];
		    int count = ((Number) result[1]).intValue();
		    System.out.println(name+" "+count);
		}*/
		model.addAttribute("resumeGroup",resumeGroup);
	}
	
	public void list(Model model){
		Map<String,Object> modelMap=model.asMap();
		Integer page=0;
		Integer size=30;
		String userId=modelMap.get("loginId").toString();
		Resume resume=new Resume();
		resume.setUserId(userId);
		PageRequest pr=PageRequest.of(page,size,Sort.by(Sort.Direction.ASC,"editDate"));
		Page<Resume> resumes=resumeRepository.findAll(Example.of(resume), pr);
		model.addAttribute("resumes",resumes);
	}
	
	public void listAll(Model model){
		Integer page=0;
		Integer size=32768;
		Resume resume=new Resume();
		PageRequest pr=PageRequest.of(page,size);
		Page<Resume> resumes=resumeRepository.findAll(Example.of(resume), pr);
		model.addAttribute("resumes",resumes);
	}
	
	public void content(Model model){
		Map<String,Object> modelMap=model.asMap();
		int id=Integer.parseInt(modelMap.get("id").toString());
		Resume resume=resumeRepository.findById(id).get();//id로 board 찾기		
		model.addAttribute("resume",resume);
	}

	public void write(Model model) {
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String userId=(String)modelMap.get("loginId");
		Resume resume=mapper.convertValue(map, Resume.class);//board로 변환
		resume.setUserId(userId);
		resume.setEditDate(new Date());
		resumeRepository.save(resume);
		model.addAttribute("map",resume);
	}
	
	public void edit(Model model){
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		int id=Integer.parseInt(modelMap.get("id").toString());
		Resume resume=mapper.convertValue(map, Resume.class);//board로 변환
		resume.setId(id);
		System.out.println(resume.getTags());
		resumeRepository.update(resume);
	}
	
	public void autoComplete(Model model){
		String keyword=(String)model.getAttribute("keyword");
		Integer page=0;
		Integer size=30;
		PageRequest pr=PageRequest.of(page,size);
		Page<Sentence> pageList=sentenceRepository.findByTextLike(keyword+"%", pr);
		System.out.println(pageList.getNumberOfElements());
		List<Sentence> list=pageList.getContent();
		model.addAttribute("list",list);
	}

	public void compareAll(Model model) {
		String answer=(String)model.getAttribute("sentence");
		String[] strs=answer.split(" ");
		int [] scores=new int[strs.length];
		int rates=0;
		int size=5;
		for(int i=0;i<strs.length;i++) {
			String like="";
			int j;
			for(j=0;j<size && i+j<strs.length;j++) {
				if(strs[i+j].length()>1)
					like+=strs[i+j]+" ";		
			}
			List<?> sentences=entityManager
	        .createNativeQuery("SELECT text FROM Sentence "
	        		+ "where match(text) against('\""+like+"\"' in boolean mode)")
	        .getResultList();
			
			for(j=0;j<size && i+j<strs.length;j++) {
				int add=Math.min(sentences.size(),size-scores[i+j]);
				scores[i+j]+=add;
				rates+=add;
			}
			if(j<size)
				break;
		}
		model.addAttribute("results",strs);
		model.addAttribute("scores",scores);
		model.addAttribute("rates",100*rates/(size*strs.length));
	}
}
