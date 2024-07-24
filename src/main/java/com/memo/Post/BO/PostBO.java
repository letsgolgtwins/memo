package com.memo.Post.BO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.Post.domain.Post;
import com.memo.Post.mapper.PostMapper;
import com.memo.common.FileManagerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j //방법3
@Service
public class PostBO {
	//방법1 private Logger log = LoggerFactory.getLogger(PostBO.class); 
	//방법2 private Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired // @Component 어노테이션을 붙여줫다는 건 Spring bean이라는 소리임으로 이렇게 가져와야 한다.
	private FileManagerService fileManagerService;
	
	// db에서 글 목록 select
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// db에 글 쓰기 - insert
	public void addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		// postMapper에는 userLoginId를 안보내도 된다. 
		
		String imagePath = null; // 초기값은 null
		if (file != null) {
			// 업로드 할 이미지가 있을 때에만 업로드
			imagePath = fileManagerService.uploadFile(file, userLoginId);
		}
		
		// 일단 db에 insert되지 않게 주석 처리
		postMapper.insertPost(userId, subject, content, imagePath); 
	}
	
	// db에서 글 상세 조회 - 단건 select
	public Post getPostByPostIdUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdUserId(postId, userId);
	}
	
	// 글 수정  - db에서 update
	// i: 파라미터들 / o: void(굳이 안받겠다)
	public void updatePostByPostId(int userId, String userLoginId, int postId, String content, String subject, MultipartFile file) {
		// update를 할 기존글을 가져온다. (1. 이미지 교체시 기존의 이미지를 삭제하기 위해서 2. 업데이트 대상이 있는지 확인 )
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		if (post == null) {
			log.warn("[글 수정부분] post is null. userId:{}, postId:{}", userId, postId);
			return;
		}
		
		// 파일이 있으면
		// 1. 새 이미지를 업로드 
		// 2. 1.단계가 성공하면 기존이미지가 있을 때, 삭제하겠다.
		String imagePath = null;
		
		if (file != null) {

			// 새 이미지 업로드
			imagePath = fileManagerService.uploadFile(file, userLoginId);
			
			// 업로드 성공 시 (null 아님) 기존 이미지가 있으면 제거
			if (imagePath != null && post.getImagePath() != null) {
				// 폴더와 이미지 제거(서버에서)
				fileManagerService.deleteFile(post.getImagePath());
			}
		}
		
		// db
	}
}
