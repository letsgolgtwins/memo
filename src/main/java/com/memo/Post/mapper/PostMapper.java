package com.memo.Post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.Post.domain.Post;


@Mapper
public interface PostMapper {

	// Test 용도 이므로 나중에 사용하지 말 것.
	public List<Map<String, Object>> selectPostListTest(); // Test를 붙인 이유는 나중에 사용하지 않도록
	
	// db에서 글 목록 select
	public List<Post> selectPostListByUserId(int userId);
	
	// 글 쓰기 - db에 컨텐츠 insert
	public Post insertPostListByUserId(
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath
			);
}
