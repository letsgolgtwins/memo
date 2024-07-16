package com.memo.Post;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memo.Post.BO.PostBO;
import com.memo.Post.domain.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {

	@Autowired
	private PostBO postBO;
	
	// 메모글 목록
	// http://localhost/post/post-list-view
	@GetMapping("/post-list-view")
	public String postListView(HttpSession session, Model model) {
		// 로그인 여부 확인
		// 에러가 안나게 하려면 Integer로 해서 null이 들어갈 수 있게.
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) { // 비로그인인 상태
			// 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		}
		// 위의 if문을 거쳤다는 건 로그인 된 상태란 것이므로 이제 db에서 가져와서 뿌리겠다.
		// DB 조회
		List<Post> postList = postBO.getPostListByUserId(userId);
		
		// 모델에 담기
		model.addAttribute("result", postList);
		
		return "post/postList";
	}
	
	// 글 쓰기 화면
	// http://localhost/post/post-create-view
	@GetMapping("/post-create-view")
	public String postCreateView() {
		return "post/postCreate";
	}
	
}
