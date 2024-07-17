package com.memo.Post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
}
