package com.ezjobs.mystory.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezjobs.mystory.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>{
	
	Page<Tag> findByNameContaining(Pageable pageable, String sch);
	
	@Transactional
	@Modifying	// update Query
	@Query(value="update Tag t set t.name = :#{#tag.name}"	+ " WHERE t.id = :#{#tag.id}")
	void update(Tag tag);

	@Transactional
	@Modifying	// delete Query
	@Query(value="delete from Tag t" + " WHERE t.id = :#{#tag.id}")
	void delete(Tag tag);
	
}
