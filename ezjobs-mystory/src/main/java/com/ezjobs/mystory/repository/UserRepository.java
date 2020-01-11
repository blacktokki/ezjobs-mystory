package com.ezjobs.mystory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezjobs.mystory.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	Page<User> findByIdContaining(Pageable pageable, String id);
	
	@Transactional
	@Modifying	// update , delete Query
	@Query(value="update User u set u.loginPw = :#{#user.loginPw}, u.name = :#{#user.name},u.email = :#{#user.email},u.sex = :#{#user.sex},u.grad = :#{#user.grad}"
			+ " WHERE u.id = :#{#user.id}")
	void update(User user);

	@Transactional
	@Modifying
	@Query(value="update User u set u.name = :#{#user.name},u.email = :#{#user.email},u.sex = :#{#user.sex},u.grad = :#{#user.grad}"
	+ " WHERE u.id = :#{#user.id}")
	void updateWithoutPw(User user);
	
	@Transactional
	@Modifying
	@Query(value="update User u set u.loginPw = :#{#user.loginPw}"
	+ " WHERE u.id = :#{#user.id}")
	void updatePw(User user);

	@Transactional
	@Modifying
	@Query(value="update User u set u.loginFailureCnt = 0"
			+ " WHERE u.id = :id")
	void clearLoginFailureCount(String id);
	
	@Transactional
	@Modifying
	@Query(value="update User u set u.loginFailureCnt = u.loginFailureCnt + 1"
			+ " WHERE u.id = :id")
	void addLoginFailureCount(String id);
}
