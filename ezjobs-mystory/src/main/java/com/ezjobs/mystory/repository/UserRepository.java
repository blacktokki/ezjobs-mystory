package com.ezjobs.mystory.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezjobs.mystory.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByLoginIdAndLoginPw(String loginId, String loginPw);
}
