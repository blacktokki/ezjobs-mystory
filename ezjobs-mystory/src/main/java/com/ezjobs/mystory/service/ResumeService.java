package com.ezjobs.mystory.service;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Sentence;
import com.ezjobs.mystory.entity.Synonym;
import com.ezjobs.mystory.repository.ResumeRepository;
import com.ezjobs.mystory.repository.SentenceRepository;
import com.ezjobs.mystory.repository.SynonymRepository;
import com.ezjobs.mystory.service.page.DefaultPageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResumeService implements DefaultPageService,AdminService<Resume>{
	
	ResumeRepository resumeRepository;
	
	SentenceRepository sentenceRepository;
	
	SynonymRepository synonymRepository;
	
	ObjectMapper mapper;

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Object[]> group(){
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
		return resumeGroup;
	}
	
	public Page<Resume> list(Map<String,Object> map){
		Boolean isAdmin=(Boolean)map.get("isAdmin");
		String op= String.valueOf(map.get("op"));
		String keyword = String.valueOf(map.get("keyword"));

		Resume resume=new Resume();
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
		if(!isAdmin)
			resume.setUserId((String)map.get("id"));
		
		Page<Resume> resumes;
		PageRequest pr=getPageRequest(map,Sort.by(Sort.Direction.ASC,"editDate"));
		System.out.println(op);
		if((keyword.equals("null")|| keyword.equals(""))&& isAdmin) {
			List<Resume> resumeList=resumeRepository.findAll(Example.of(resume));
			int size=(Integer)map.get("size");
			int form=size*((Integer)map.get("page"));
			resumes=new PageImpl<>(resumeList.subList(form,form+size), pr, resumeList.size());
		}
		else {
			resumes=resumeRepository.findAll(Example.of(resume), pr);
		}
		
		System.out.println(resumes.getContent().size());
		return resumes;

	}
	
	public List<Resume> listAll(){
		return resumeRepository.findAll();
	}
	
	public Resume content(Integer id){
		return resumeRepository.findById(id).get();	
	}

	public void write(Map<String,Object> map) {
		String userId=(String)map.get("userId");
		Resume resume=mapper.convertValue(map, Resume.class);//board로 변환
		resume.setUserId(userId);
		resume.setState("미작성");
		resume.setEditDate(new Date());
		resumeRepository.save(resume);
		map.put("id",resume.getId());
	}
	
	public void edit(Map<String,Object> map){
		Resume resume=mapper.convertValue(map, Resume.class);//board로 변환
		//System.out.println(resume.getTags());
		resumeRepository.update(resume);
	}
	
	public void editState(Integer id,String state){
		Resume resume=new Resume();//board로 변환
		resume.setId(id);
		resume.setState(state);
		resumeRepository.updateState(resume);
	}
	
	public void delete(Map<String, Object> map) {
		int id=Integer.parseInt(map.get("id").toString());
		resumeRepository.deleteById(id);
	}
	
	public List<Sentence> autoComplete(Map<String, Object> map){
		String keyword=(String)map.get("keyword");
		System.out.println(keyword);
		String keywordInclude=(String)map.get("keywordInclude");
		Integer page=0;
		Integer size=30;
		PageRequest pr=PageRequest.of(page,size);
		Page<Sentence> pageList=sentenceRepository.findByTextLike(keyword+"%"+keywordInclude+"%", pr);
		System.out.println(pageList.getNumberOfElements());
		return pageList.getContent();
	}

	public Map<String,Object> compareAll(String answer) {
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
		Map<String,Object> map=new HashMap<>();
		map.put("results",strs);
		map.put("scores",scores);
		map.put("rates",100*rates/(size*strs.length));
		return map;
	}

	public void tagsearch() {//시험용
		/*
		List<Sentence> sentences = entityManager
		        .createQuery("SELECT s FROM Sentence s WHERE s.text LIKE ?1 AND s.tags LIKE ?2 AND s.tags LIKE ?3",Sentence.class)
		        .setParameter(1,"%키워드%")
		        .setParameter(2,"%태그1%")
		        .setParameter(3,"%태그2%")
		        .getResultList();
		*/
	}

	public void addSynonym(Map<String,Object> map) {
		String userId=(String)map.get("loginId");
		Synonym synonym=mapper.convertValue(map, Synonym.class);//board로 변환
		synonym.setUserId(userId);
		synonym.setValid((userId.equals("_admin")));
		synonymRepository.save(synonym);
	}

	@Override
	public Page<Resume> adminListAll(Map<String, Object> map) {
		return list(map);
	}
}
