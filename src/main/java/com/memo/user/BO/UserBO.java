package com.memo.user.BO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.Entity.UserEntity;
import com.memo.user.Repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	private UserRepository userRepository;
	
	// db에서 조회
	// i: loginId o: userEntity 채워져있거나 null
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByloginId(loginId);
	}
	
	// db에 insert
	// input: 4개의 파라미터 o: userEntity
	public UserEntity addUser(String loginId, String password, String name, String email) {
		return userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
	}
}
