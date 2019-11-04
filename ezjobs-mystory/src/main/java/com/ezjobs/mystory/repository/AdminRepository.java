package com.ezjobs.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.User;

@Repository
public interface AdminRepository extends JpaRepository<User, Integer>{
	
}
