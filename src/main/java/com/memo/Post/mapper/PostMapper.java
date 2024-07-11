package com.memo.Post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

	// Test 용도 이므로 나중에 사용하지 말 것.
	public List<Map<String, Object>> selectPostListTest(); // Test를 붙인 이유는 나중에 사용하지 않도록
}
