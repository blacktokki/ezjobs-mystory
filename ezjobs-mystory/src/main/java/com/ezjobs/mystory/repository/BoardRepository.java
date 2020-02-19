package com.ezjobs.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezjobs.mystory.entity.Board;

@Repository
public interface BoardRepository<T extends Board> extends JpaRepository<T, Integer>{
	
	@Transactional
	@Modifying	// update , delete Query
	@Query(value="update Board b set b.title = :#{#board.title}, b.text = :#{#board.text} "
			+ " WHERE b.id = :#{#board.id}")
	void update(@Param("board") T board);
}
