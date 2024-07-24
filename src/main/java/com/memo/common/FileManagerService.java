package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // 범용적인 것 
public class FileManagerService {
	
	// 실제 업로드가 된 이미지가 저장될 서버의 경로
	public static final String FILE_UPLOAD_PATH = "C:\\이민희\\6_spring_project\\memo\\memo_workspace\\images/"; // 상수로 필드를 지음. 이 경우 대문자로 만들어준다. 그리고 경로 마지막에 / 를 넣어준다.

	// input: MultipartFile file, String userLoginId(근데 이건 걍 해준 것일뿐, userId로 해줘도 된다. 단지 1_17324215 이런거 보단 aaaa_173245가 더 낫게 보이니까~)
	// output: String(이미지 경로)
	public String uploadFile(MultipartFile file, String userLoginId) {
		// 폴더(디렉토리) 생성
		// 예: aaaa_240717181029
		String directoryName = userLoginId + "_" + System.currentTimeMillis(); // 현재 시간 메소드
		
		// C:\\이민희\\6_spring_project\\memo\\memo_workspace\\images/aaaa_240717181029/ 같은 주소를 만들어야 한다.
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";
		
		// 폴더 생성
		File directory = new File(filePath);
		if (directory.mkdir() == false) { // 폴더 생성 시 실패하면 경로를 null로 return (사진 잘못 올렸다고 다 지워버면 좀 그렇잖아~)
			return null;
		}
		
		// 파일 업로드
		try {
			byte[] bytes = file.getBytes(); // byte 타입& 예외처리
			// !! 한글명으로 된 이미지 파일명은 업로드가 불가하므로 나중에 영문자로 바꾸기
			Path path = Paths.get(filePath + file.getOriginalFilename());
			// 실제 업로드
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace(); // 에러 메시지를 로그에 찍겠다.(이건 걍 놔두자)
			return null; // 이미지 업로드 실패시 경로는 null로 하겠다.
		} 
		
		// 파일 업로드가 성공하면 이미지 url path를 return 
		// 주소는 이렇게 될 것이다. (예언)
		// (예시) /images/aaaa_240717181029/sun.png 이런식으로 return 되게끔 해줄 거다.
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
		
		// /images/aaaa_1721209539893/coors-field-4046946_1280.jpg
		
	}
	
	// 파일 삭제
	// i: 이미지 경로(path) / o: X
	public void deleteFile(String imagePath) { // /images/aaaa_1721209539893/coors-field-4046946_1280.jpg
		// C:\이민희\6_spring_project\memo\memo_workspace\images\aaaa_1721209539893
		
		// 주소에 겹치는 /images/ 를 지워야됨
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		
		// 삭제할 이미지가 존재하는 가?
		if (Files.exists(path)) {
			// 이미지 삭제
			try {
				Files.delete(path);
			} catch (IOException e) {
				// e.printStackTrace();
				log.info("[FileManagerService 파일삭제] 삭제 실패. path:{}", path.toString());
				return;
			}
			
			// 폴더(디렉토리) 삭제
			path = path.getParent(); // 부모 노드를 다시 페스에 저장
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					// e.printStackTrace();
					log.info("[FileManagerService 파일삭제] 디렉토리 삭제 실패. path:{}", path.toString());
					return;
				}
			}
		}
	}
}