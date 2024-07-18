package com.memo.Post.BO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.Post.domain.Post;
import com.memo.Post.mapper.PostMapper;
import com.memo.common.FileManagerService;

@Service
public class PostBO {

	@Autowired
	private PostMapper postMapper;
	
	@Autowired // @Component 어노테이션을 붙여줫다는 건 Spring bean이라는 소리임으로 이렇게 가져와야 한다.
	private FileManagerService fileManagerService;
	
	// db에서 글 목록 select
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// db에 글 쓰기 - insert
	public void addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		// postMapper에는 userLoginId를 안보내도 된다. 
		
		String imagePath = null; // 초기값은 null
		if (file != null) {
			// 업로드 할 이미지가 있을 때에만 업로드
			imagePath = fileManagerService.uploadFile(file, userLoginId);
		}
		
		// 일단 db에 insert되지 않게 주석 처리
		postMapper.insertPost(userId, subject, content, imagePath); 
	}
	
	// db에서 글 상세 조회 - 단건 select
	public Post getPostByPostIdUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdUserId(postId, userId);
	}
}
