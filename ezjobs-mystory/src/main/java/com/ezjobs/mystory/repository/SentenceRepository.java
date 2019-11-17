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

}
