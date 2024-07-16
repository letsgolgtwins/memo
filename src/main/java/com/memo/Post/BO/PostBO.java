package com.memo.Post.BO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.Post.domain.Post;
import com.memo.Post.mapper.PostMapper;

@Service
public class PostBO {

	@Autowired
	private PostMapper postMapper;
	
	// db에서 글 목록 select
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// db에 글 쓰기 insert
	public Post addPostListByUserId(String subject, String content, String imagePath) {
		return postMapper.insertPostListByUserId(subject, content, imagePath);
	}
}
