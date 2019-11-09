package com.ezjobs.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer>{
}
