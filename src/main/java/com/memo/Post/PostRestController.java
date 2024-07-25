package com.memo.Post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.Post.BO.PostBO;
import com.memo.Post.domain.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	private PostBO postBO;
	/**
	 * 글쓰기 API
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	// 글 쓰기 후 저장
	// /post/create
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject, 
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file, // file은 비필수 파라미터
			HttpSession session) { // 글쓴이 번호를 꺼내기 위해 HttpSession session이 필요함.
		
		// 글쓴이 번호를 session에서 꺼낸다.
		// 만약에 ""안에 들어갈 요소를 까먹었다면 다시 UserRestcontroller 를 가서 확인한다.
		int userId = (int) session.getAttribute("userId"); 
		
		// 2교시-내용추가
		String userLoginId = (String) session.getAttribute("userLoginId");
	
		// 글 쓰기 db에 insert
		postBO.addPost(userId, userLoginId, subject, content, file);
		
		// 응답 JSON
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("message", "글쓰기성공");
		
		return result;
	}
	
	// 글 수정 
	// /post/update
	@PutMapping("/update")
	public Map<String, Object> update(
			@RequestParam("postId") int postId,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file, // file은 비필수 파라미터
			HttpSession session ) { 
		
		// session에서 userLoginId 만 뽑아내겠다.
		int userId = (int) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		// 글 수정 - db에서 update +이미지도 마찬가지
		postBO.updatePostByPostId(userId, userLoginId, postId, content, subject, file);
		
		// 응답 JSON
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
		
	}
	
	// 글 삭제 API
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		// session에서 userId 꺼내기
		int userId = (int) session.getAttribute("userId");
		
		// db에서 delete
		postBO.deletePostByPostIdAndUserId(postId, userId);
		
		// 응답 JSON
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	
	}
	
}
