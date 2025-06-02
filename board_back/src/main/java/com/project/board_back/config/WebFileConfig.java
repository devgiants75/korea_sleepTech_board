package com.project.board_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebFileConfig implements WebMvcConfigurer {

    /**
     * 정적 리소스 경로 매핑 설정
     * - C:/upload/file 폴더 내의 파일을 URL 경로 "/files/**"로 접근 가능하도록 설정
     * - 예: http://localhost:8080/files/파일명
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**") // 요청 경로
                .addResourceLocations("file:C:/upload/file/"); // 실제 로컬 경로
    }
}