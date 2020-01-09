package com.ezjobs.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.Resume;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {

	@Transactional
	@Modifying // update , delete Query
	@Query(value = "update Resume r set "
			+ "r.question = :#{#resume.question}, r.tags = :#{#resume.tags}, r.answer = :#{#resume.answer},"
			+ " r.company = :#{#resume.company}, r.closeDate = :#{#resume.closeDate} "
			+ " WHERE r.id = :#{#resume.id}")
	void update(@Param("resume") Resume resume);

	@Transactional
	@Modifying // update , delete Query
	@Query(value = "update Resume r set r.state = :#{#resume.state} "
			+ " WHERE r.id = :#{#resume.id}")
	void updateState(Resume resume);
	
}