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
import org.springframework.web.bind.annotation.RequestParam;

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
	public String postListView(HttpSession session, Model model,
			@RequestParam(value = "prevId", required = false) Integer prevIdParam,
			@RequestParam(value = "nextId", required = false) Integer nextIdParam) {
		// 로그인 여부 확인
		// 에러가 안나게 하려면 Integer로 해서 null이 들어갈 수 있게.
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) { // 비로그인인 상태
			// 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		}
		// 위의 if문을 거쳤다는 건 로그인 된 상태란 것이므로 이제 db에서 가져와서 뿌리겠다.
		// DB 조회
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		int prevId = 0;
		int nextId = 0;
		if (postList.isEmpty() == false) { // 글 목록이 비어있지 않을 때 페이징 정보 세팅
			prevId = postList.get(0).getId(); // 첫 번째 칸 글의 글 번호
			nextId = postList.get(postList.size() - 1).getId(); // 마지막 칸 글의 글 번호
		
			// 이전 방향의 끝인가? 그러면 0
			// prevId와 테이블의 제일 큰 숫자와 같으면 이전의 끝페이지
			if (postBO.isPrevLastPageByUserId(userId, prevId)) {
				prevId = 0;
			}
			
			// 다음 벙향의 끝인가? 그러면 0
			// nextId와 테이블의 제일 작은 숫자가 같으면 다음의 끝페이지
			if (postBO.isNextLastPageByUserId(userId, nextId)) {
				nextId = 0;
			}
		}
		
		// 모델에 담기
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);
		
		model.addAttribute("result", postList);
		
		return "post/postList";
	}
	
	// 글 쓰기 화면
	// http://localhost/post/post-create-view
	@GetMapping("/post-create-view")
	public String postCreateView() {
		return "post/postCreate";
	}
	
	// 글 상세 화면
	// http://localhost/post/post-detail-view
	@GetMapping("/post-detail-view")
	public String postDetailView(@RequestParam("postId") int postId, Model model, HttpSession session) {
		// userId까지 꺼내오겟다
		int userId = (int) session.getAttribute("userId");
		
		// db에서 단건 조회
		Post post = postBO.getPostByPostIdUserId(postId, userId);
		
		// model에 담기
		model.addAttribute("postInfo", post);
		
		// 화면 이동
		return "post/postDetail";
	}
	
}
