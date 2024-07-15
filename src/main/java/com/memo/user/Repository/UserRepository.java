package com.memo.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.user.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	// JPQL
	// loginId 중복확인 - db에서 loginId 조회
	public UserEntity findByloginId(String loginId);
	
	// 로그인  
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
}
