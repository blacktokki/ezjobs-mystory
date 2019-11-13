package com.ezjobs.mystory.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezjobs.mystory.entity.Board;
import com.ezjobs.mystory.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByLoginIdAndLoginPw(String loginId, String loginPw);
	
	@Transactional
	@Modifying	// update , delete Query
	@Query(value="update User u set u.loginPw = :#{#user.loginPw}"
			+ " WHERE u.loginId = :#{#user.loginId}")
	void update(User user);
	
}
