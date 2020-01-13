package com.ezjobs.mystory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.*;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Integer>{
	
	//자동완성용
	public Page<Sentence> findByTextLike(String text,Pageable pageable);
	
	//태그 없이 부분문자열 검색
	public Page<Sentence> findByTextContains(String text, Pageable pageable);
	
	//AND
	//태그만 1개
	public Page<Sentence> findByTagsContains(String tag1, Pageable pageable);
	
	//태그만 2개
	public Page<Sentence> findByTagsContainsAndTagsContains(String tag1, String tag2, Pageable pageable);
	
	//태그만 3개
	public Page<Sentence> findByTagsContainsAndTagsContainsAndTagsContains(String tag1, String tag2, String tag3, Pageable pageable);
	
	//검색어 + 태그 1개
	public Page<Sentence> findByTextContainsAndTagsContains(String text, String tag1, Pageable pageable);
	
	//검색어 + 태그 2개
	public Page<Sentence> findByTextContainsAndTagsContainsAndTagsContains(String text, String tag1, String tag2, Pageable pageable);

	//검색어 + 태그 3개
	public Page<Sentence> findByTextContainsAndTagsContainsAndTagsContainsAndTagsContains(String text, String tag1, String tag2, String tag3, Pageable pageable);
	
	
	//OR
	//태그 1개
	//AND와 동일
	
	//태그 2개
	public Page<Sentence> findByTagsContainsOrTagsContains(String tag1, String tag2, Pageable pageable);
	
	//태그 3개
	public Page<Sentence> findByTagsContainsOrTagsContainsOrTagsContains(String tag1, String tag2, String tag3, Pageable pageable);
	
	//검색어 + 태그 1개
	//AND와 동일
		
	//검색어 + 태그 2개
	public Page<Sentence> findByTextContainsAndTagsContainsOrTagsContains(String text, String tag1, String tag2, Pageable pageable);
		
	//검색어 + 태그 3개
	public Page<Sentence> findByTextContainsAndTagsContainsOrTagsContainsOrTagsContains(String text, String tag1, String tag2, String tag3, Pageable pageable);
		
}
