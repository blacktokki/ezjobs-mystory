package com.ezjobs.mystory.service;

import java.util.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Sentence;
import com.ezjobs.mystory.entity.Synonym;
import com.ezjobs.mystory.repository.ResumeRepository;
import com.ezjobs.mystory.repository.SentenceRepository;
import com.ezjobs.mystory.repository.SynonymRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResumeService {
	
	@Inject
	ResumeRepository resumeRepository;
	
	@Inject
	SentenceRepository sentenceRepository;
	
	@Inject
	SynonymRepository synonymRepository;
	
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
		Map<String, Object> modelMap = model.asMap();
		Map<?, ?> map = (Map<?, ?>) modelMap.get("map");
		Resume resume=new Resume();
		Boolean isAdmin=(Boolean)modelMap.get("isAdmin");
		String page ="1";
		String size ="20";
		String op=null;
		if (map!=null) {
			page = Optional.ofNullable((String) map.get("page")).orElse(page);// String으로 담음
			size = Optional.ofNullable((String) map.get("size")).orElse(size);
			op = String.valueOf(map.get("op"));
			String keyword = String.valueOf(map.get("keyword"));
			model.addAttribute("op", op);
			model.addAttribute("keyword", keyword);
			System.out.println(keyword);
			System.out.println(op);
			if (op.equals("tagSearch")) { // 태그명 검색
				resume.setTags(keyword);
			}
			else if (op.equals("question")) { // 자소서 문항 검색
				resume.setQuestion(keyword);
			}
			else if (isAdmin && op.equals("userId")) { // 작성자 검색
				resume.setUserId(keyword);
			}
			else if (op.equals("company")) { // 회사명 검색
				resume.setCompany(keyword);
			}
			else if (op.equals("state")) { // 상태명 검색
				resume.setState(keyword);
			}
		}
		
		if(!isAdmin)
			resume.setUserId((String)modelMap.get("loginId"));
		int pageNum = Integer.parseInt(page) - 1;// 값이없을경우 0 //shownum->size, 
		int sizeNum = Integer.parseInt(size);
		model.addAttribute("size", sizeNum);
		
		Page<Resume> resumes;
		PageRequest pr=PageRequest.of(pageNum,sizeNum,Sort.by(Sort.Direction.ASC,"editDate"));
		if(op!=null|| !isAdmin) {
			resumes=resumeRepository.findAll(Example.of(resume), pr);
		}
		else {
			List<Resume> resumeList=resumeRepository.findAll(Example.of(resume));
			int form=sizeNum*pageNum;
			resumes=new PageImpl<>(resumeList.subList(form,form+sizeNum), pr, resumeList.size());
		}
		System.out.println(resumes.getContent().size());
		model.addAttribute("resumes", resumes);
		model.addAttribute("pageNavNumber", resumes.getNumber() / 5);
	}
	
	public void listAll(Model model){
		Map<?,?> map=(Map<?,?>)model.getAttribute("map");
		Integer page=Optional.of((Integer)map.get("page")).orElse(0);
		Integer size=Optional.of((Integer)map.get("size")).orElse(32768);
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
		resume.setState("미작성");
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
		//System.out.println(resume.getTags());
		resumeRepository.update(resume);
	}
	
	public void editState(Model model){
		Map<String,Object> modelMap=model.asMap();
		int id=Integer.parseInt(modelMap.get("id").toString());
		String state=modelMap.get("state").toString();
		Resume resume=new Resume();//board로 변환
		resume.setId(id);
		resume.setState(state);
		resumeRepository.updateState(resume);
	}
	
	public void delete(Model model) {
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		int id=Integer.parseInt(map.get("id").toString());
		resumeRepository.deleteById(id);
	}
	
	public void autoComplete(Model model){
		Map<?,?> map=(Map<?,?>)model.getAttribute("map");
		String keyword=(String)map.get("keyword");
		System.out.println(keyword);
		String keywordInclude=(String)map.get("keywordInclude");
		Integer page=0;
		Integer size=30;
		PageRequest pr=PageRequest.of(page,size);
		Page<Sentence> pageList=sentenceRepository.findByTextLike(keyword+"%"+keywordInclude+"%", pr);
		System.out.println(pageList.getNumberOfElements());
		List<Sentence> list=pageList.getContent();
		model.addAttribute("list",list);
	}

	public void compareAll(Model model) {
		String answer=((String)model.getAttribute("sentence"));
		String[] strs=answer.split("\\s+");
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
	        .createNativeQuery("SELECT text FROM sentence "
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

	public void tagsearch(Model model) {
		/*
		List<Sentence> sentences = entityManager
		        .createQuery("SELECT s FROM Sentence s WHERE s.text LIKE ?1 AND s.tags LIKE ?2 AND s.tags LIKE ?3",Sentence.class)
		        .setParameter(1,"%키워드%")
		        .setParameter(2,"%태그1%")
		        .setParameter(3,"%태그2%")
		        .getResultList();
		*/
	}

	public void addSynonym(Model model) {
		Map<String,Object> modelMap=model.asMap();
		Map<?,?> map=(Map<?, ?>)modelMap.get("map");
		String userId=(String)modelMap.get("loginId");
		Synonym synonym=mapper.convertValue(map, Synonym.class);//board로 변환
		synonym.setUserId(userId);
		synonym.setValid((userId.equals("_admin")));
		synonymRepository.save(synonym);
	}
}
