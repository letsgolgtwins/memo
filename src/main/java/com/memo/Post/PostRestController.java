package com.memo.Post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.Post.BO.PostBO;
import com.memo.Post.domain.Post;

@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	private PostBO postBO;
	
	// 글 쓰기 후 저장
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject, 
			@RequestParam("content") String content,
			@RequestParam("imagePath") String imagePath) {
		
		// 글 쓰기 db에 insert
		Post postContents = postBO.addPostListByUserId(subject, content, imagePath);
		
		// 응답 JSON
		Map<String, Object> result = new HashMap<>();
		if (postContents != null) { // 즉, 글쓰기에 성공한 셈
			result.put("code", 200);
			result.put("message", "글쓰기성공");
		} else { // 즉, 글쓰기에 실패한 셈
			result.put("code", 500);
			result.put("error_message", "실패");
		}
		return result;
		
	}
}
