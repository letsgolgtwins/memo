package com.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {

	/**
	 * 회원가입 화면
	 * @return
	 */
	// 회원가입 화면
	// http://localhost/user/sign-up-view
	@GetMapping("/sign-up-view")
	public String signUpView() {
		// 가운데 레이아웃 조각만 내려주면 전체 레이아웃으로 구성된다.
		return "user/signUp"; 
	}
	
	/**
	 * 로그인 화면
	 * @return
	 */
	// 로그인 화면
	// http://localhost/user/sign-in-view
	@GetMapping("/sign-in-view")
	public String signInView() {
		return "user/signIn";
	}
	
	/**
	 * 로그아웃 API
	 * @param session
	 * @return
	 */
	// 로그아웃 구현
	// post 나 get 둘 다 허용이므로 requestMapping으로 하자
	@RequestMapping("sign-out")
	public String signOut(HttpSession session) {
		// session 내용 비움
		session.removeAttribute("userId");
		session.removeAttribute("userloginId");
		session.removeAttribute("userName");
		
		// 로그인 페이지로 이동
		return "redirect:/user/sign-in-view"; // 로그인 페이지 리다이렉트
	}
}
