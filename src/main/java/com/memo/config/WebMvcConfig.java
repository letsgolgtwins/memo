package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;

@Configuration // 설정을 위한 Spring bean
public class WebMvcConfig implements WebMvcConfigurer {

	// 이미지 path와 서버에 업로드된 실제 이미지와 매핑 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 가독성을 위해 한 줄 한 줄 떨어뜨리겠다.
		registry.addResourceHandler("/images/**") // web path http://localhost/images/aaaa_1721209539893/coors-field-4046946_1280.jpg
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 이미지 파일 위치 window의 경우 슬래시 3개(///)
	}
}