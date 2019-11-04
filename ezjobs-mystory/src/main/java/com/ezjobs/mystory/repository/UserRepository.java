package com.ezjobs.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
}
