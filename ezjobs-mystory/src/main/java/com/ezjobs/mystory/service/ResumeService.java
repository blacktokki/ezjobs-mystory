package com.ezjobs.mystory.service;

import java.math.BigInteger;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.entity.Sentence;
import com.ezjobs.mystory.entity.Synonym;
import com.ezjobs.mystory.entity.Tag;
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
	
	public Page<Resume> list(Map<String,Object> map){
		Boolean isAdmin=(Boolean)map.get("isAdmin");
		String op= String.valueOf(map.get("op"));
		String keyword = String.valueOf(map.get("keyword"));

		Resume resume=new Resume();
		/*
		if (op.equals("tagSearch")) { // 태그명 검색
			resume.setTags(keyword);
		}
		else */if (op.equals("question")) { // 자소서 문항 검색
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
		if((keyword.equals("null")|| keyword.equals(""))&& isAdmin) {
			resumes=resumeRepository.findAllByIdGreaterThan(0, pr);
		}
		else {
			resumes=resumeRepository.findAll(Example.of(resume), pr);
		}
		return resumes;

	}
	
	public List<Resume> listAll(){
		return resumeRepository.findAll();
	}
	
	public Resume content(Integer id){
		return resumeRepository.findById(id).get();	
	}
	
	public void write(Map<String,Object> map,Set<Tag> tagsSet) {
		Resume resume=mapper.convertValue(map, Resume.class);//board로 변환
		tagsSet.forEach(tag->tag.getResumes().add(resume));
    	resume.setTags(tagsSet);
    	resumeRepository.save(resume);
		map.put("id", resume.getId());
		
	}
	
	public void edit(Map<String,Object> map,Set<Tag> tagsSet){
		Resume resume=mapper.convertValue(map, Resume.class);
		Resume resumeOld=resumeRepository.getOne(Integer.parseInt((String)map.get("id")));
		for(Tag tag:tagsSet) {
			tag.getResumes().remove(resumeOld);
			tag.getResumes().add(resume);
		}
    	resume.setTags(tagsSet);
		resumeRepository.save(resume);
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
		String[] typeStrs = {};
		String[] deptStrs = {};
		Boolean isStart=Boolean.parseBoolean(((String)map.get("isStart")));
		Boolean isEnd=Boolean.parseBoolean(((String)map.get("isEnd")));
		Integer size=30;
		try {
			typeStrs=mapper.readValue((String)map.get("types"), String[].class);
			deptStrs=mapper.readValue((String)map.get("depts"), String[].class);
		}
		catch(Exception e) {
		}
		
		CriteriaBuilder builder=entityManager.getCriteriaBuilder();
		CriteriaQuery<Sentence> query = builder.createQuery(Sentence.class);
		Root<Sentence> m = query.from(Sentence.class);
		//검색조건 정의
		List<Predicate> typesList=new ArrayList<>();
		List<Predicate> deptsList=new ArrayList<>();
		List<Predicate> position=new ArrayList<>();
		for(String item:typeStrs)
			typesList.add(builder.like(m.get("tags"), "%"+item+"%"));	
		for(String item:deptStrs)
			deptsList.add(builder.like(m.get("tags"), "%"+item+"%"));
		if(isStart) {
			position.add(builder.lessThanOrEqualTo(m.get("position"), 1));
		}
		if(isEnd) {
			position.add(builder.equal(
				builder.diff(m.get("positionMax"), m.get("position")), 1));
		}
		List<Predicate> wheres=new ArrayList<>();
		List<List<Predicate>> wheresArray=
				Arrays.asList(typesList,deptsList,position);
		wheres.add(builder.like(m.get("text"), keyword+"%"));
		for(List<Predicate> list:wheresArray) {
			if(list.size()>0)
				wheres.add(builder.or(list.toArray(
					new Predicate[list.size()])
				));
		}
		//정렬조건 정의
		//Order ageDesc = builder.desc(m.get("age"));
		//distinct 중복제거.
		query.select(m)
			 .distinct(true)
			 .where(builder.and(wheres.toArray(new Predicate[wheres.size()])));
		List<Sentence> list=entityManager.createQuery(query).setMaxResults(size).getResultList();	
		return list;
	}

	public Map<String,Object> compareAll(String answer) {
		String[] strs=answer.split("\\s+");
		int [] scores=new int[strs.length];
		int [] scoresMax=new int[strs.length];
		int rates=0;
		int ratesMax=0;
		int size=Math.min(5,strs.length);
		int repeat=strs.length-size+1;
		String queries="";
		String[] likes=new String[repeat];
		
		for(int i=0;i<repeat;i++) {
			if (i>0)
				queries+=" union ";
			queries+="SELECT ?0,count(*) FROM sentence where match(text) against( ?1 in boolean mode)"
				.replace("?0",""+i)
				.replace("?1","?"+(i+1));
			likes[i]="";
			for(int j=0;j<size;j++) {
				if(strs[i+j].length()>1)
					likes[i]+=strs[i+j]+" ";	
			}
		}
		Query query=entityManager.createNativeQuery(queries);
		for(int i=0;i<repeat;i++)
			query.setParameter(i+1, "\""+likes[i]+"\"");
		List<?> list=query.getResultList();
		for(int i=0;i<repeat;i++) {
			int cnt =((BigInteger)((Object[])list.get(i))[1]).intValue();
			int add=cnt>0?1:0;
			for(int j=0;j<size;j++) {
				scores[i+j]+=add;
				scoresMax[i+j]+=1;
				rates+=add;
				ratesMax+=1;
			}
		}
		
		Map<String,Object> map=new HashMap<>();
		map.put("results",strs);
		map.put("scores",scores);
		map.put("scoresMax",scoresMax);
		map.put("rates",100*rates/ratesMax);
		return map;
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
