package com.ezjobs.mystory.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.repository.ResumeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResumeService {
	
	@Inject
	ResumeRepository resumeRepository;
	
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
		//Map<String,Object> modelMap=model.asMap();
		//Integer.parseInt(modelMap.get("userId").toString());
		Integer page=0;
		Integer size=30;
		String userId="_SI";
		String group="userId";
		String groupValue="_SI";
		List<Object[]> resumes = entityManager
		        .createQuery("SELECT id,question,answer FROM Resume as r "
		        		+ "WHERE userId=?1 AND "+group+"= ?2",Object[].class)
		        .setParameter(1, userId)
		        .setParameter(2, groupValue)
		        .setMaxResults(size)
		        .setFirstResult(page * size)
		        .getResultList();
		model.addAttribute("resumes",resumes);
	}
	
	public void content(Model model){
		Map<String,Object> modelMap=model.asMap();
		int id=Integer.parseInt(modelMap.get("id").toString());
		Resume resume=resumeRepository.findById(id).get();//id로 board 찾기		
		model.addAttribute("resume",resume);
	}
}
