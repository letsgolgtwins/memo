package com.memo.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.Post.mapper.PostMapper;

@Controller
public class TestController {

	@Autowired
	private PostMapper postMapper;
	
	// html 테스트
	// http://localhost/test1
	@ResponseBody
	@GetMapping("/test1")
	public String test1() {
		return "hello world!";
	}
	
	// JSON 테스트
	// http://localhost/test2
	@ResponseBody
	@GetMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("LG 트윈스", "한국프로야구 최고의 팀");
		map.put("유격수", "오지환");
		return map;
	}
	
	// 타임리프 테스트
	// http://localhost/test3
	@GetMapping("/test3")
	public String test3() {
		return "Test/test3";
	}
	
	// MyBatis 테스트
	// http://localhost/test4
	@ResponseBody
	@GetMapping("/test4")
	public List<Map<String, Object>> test4 () {
		// 원래는 List<이 안에 도메인으로 들어와야 하지만 태스트이기 때문에 귀찮으므로 map으로 할 것. 즉, map이나 domain이나 다를 게 없다는 걸 알아야 함
		return postMapper.selectPostListTest();
	}
}
