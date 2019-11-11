package com.ezjobs.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.*;

@Repository
public interface SynonymRepository extends JpaRepository<Synonym, Integer>{
	
}
