package com.capstone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Value("${app.upload.dir:${user.home}/uploads/images}")
    private String uploadDir;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드된 이미지 파일에 접근하기 위한 리소스 핸들러
        Path uploadPath = Paths.get(uploadDir);
        String uploadAbsolutePath = uploadPath.toFile().getAbsolutePath();
        
        System.out.println("Upload directory path: " + uploadAbsolutePath);
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + uploadAbsolutePath + "/") // Windows는 file:/// 형식으로 경로 지정
                .setCachePeriod(0); // 캐싱 비활성화
    }
} 