package com.memo.Post.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data // 이게 getter setter 대용
public class Post {

	// 필드
	private int id;
	private int userId;
	private String subject;
	private String content;
	private String imagePath;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
