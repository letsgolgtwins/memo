package com.memo.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.user.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	// JPQL
	// db에서 loginId 조회
	public UserEntity findByloginId(String loginId);
}
