package com.ezjobs.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.BoardArchive;

@Repository
public interface BoardArchiveRepository extends JpaRepository<BoardArchive, Integer>{
}
