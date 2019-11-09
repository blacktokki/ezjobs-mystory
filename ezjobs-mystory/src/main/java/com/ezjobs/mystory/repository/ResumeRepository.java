package com.ezjobs.mystory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer>{
	Page<Resume> findByTypeContainingOrDeptContainingOrCompanyContainingOrQuestionContainingOrAnswerContainingOrUserIdContaining(Pageable pageable, String type, String dept, String company, String question, String answer, String userId);
	Page<Resume> findByQuestionContaining(Pageable pageable, String question);
	Page<Resume> findByAnswerContaining(Pageable pageable, String answer);
}