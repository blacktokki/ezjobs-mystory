package com.ezjobs.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
}
