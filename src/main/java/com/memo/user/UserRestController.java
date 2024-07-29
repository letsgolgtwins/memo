package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.BO.UserBO;
import com.memo.user.Entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {

	@Autowired
	private UserBO userBO;
	
	/**
	 * 아이디 중복확인 API
	 * @param loginId
	 * @return
	 */
	// db에서 loginId가 중복인지 조회
	@RequestMapping("/is-duplicated-id") // 이거 getmapping이나 마찬가지 아닌가? > duplicateCheckBtn 클릭 이벤트의 ajax의 type이 get으로 했으니까?
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		// 이슈 테스트 2트
		
		// db 조회
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		// 응답 JSON
		Map<String, Object> map = new HashMap<>();
		if (user != null) { // null이 아니면 즉, 중복이면
			map.put("code", 200);
			map.put("is_duplicated_id", true);
		} else { // null 이라면 즉, 중복이 아니라면
			map.put("is_duplicated_id", false);
		}
		return map;
	}
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// password 암호화 md5 알고리즘 => hashing
		// aaaa => 74b8733745420d4d33f80c4663dc5e5
		String hashedPassword = EncryptUtils.md5(password);
		
		// db에 insert
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		// 응답 JSON
		Map<String, Object> result = new HashMap<>();
		if (user != null) { // 성공
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패하였습니다.");
		}
		return result;
	}
	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	// 로그인 - db에서 select
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			// 이때 파라미터들은 signIn.html의 name 속성들
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) { 
		
		// password hashing (그냥 password 넣어봤자 무의미)
		String hashedPassword = EncryptUtils.md5(password);
		
		// db에서 select 조회(UserEntity로 받아옴) - parameter는 loginId, hashedPassword
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		// 로그인 유지 처리 및 응답 JSON
		Map<String, Object> map = new HashMap<>();
		if (user != null) { // 로그인에 성공하였을 때
			// 세션에 사용자 정보를 담는다. (사용자 각각 마다)
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			map.put("code", 200);
			map.put("result", "성공");
		} else { // 로그인에 실패했을 때
			map.put("code", 403);
			map.put("error_message", "존재하지 않는 사용자입니다.");
		}
		return map;
	}
}
