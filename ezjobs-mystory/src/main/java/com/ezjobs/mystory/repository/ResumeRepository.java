package com.ezjobs.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezjobs.mystory.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer>{
	@Transactional
	@Modifying	// update , delete Query
	@Query(value="update Resume r set r.question = :#{#resume.question}, r.answer = :#{#resume.answer} "
			+ " WHERE r.id = :#{#resume.id}")
	void update(@Param("resume") Resume resume);

}
