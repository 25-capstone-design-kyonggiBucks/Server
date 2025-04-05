package com.capstone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path imageUploadPath = Paths.get(uploadDir, "uploads", "images");
        Path videoUploadPath = Paths.get(uploadDir, "uploads", "videos");

        String imageResourcePath = imageUploadPath.toUri().toString();
        String videoResourcePath = videoUploadPath.toUri().toString();

        System.out.println("이미지 경로: " + imageResourcePath);
        System.out.println("영상 경로: " + videoResourcePath);

        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations(imageResourcePath)
                .setCachePeriod(0); // 캐시 안 함

        registry.addResourceHandler("/uploads/videos/**")
                .addResourceLocations(videoResourcePath)
                .setCachePeriod(0);
    }
} 