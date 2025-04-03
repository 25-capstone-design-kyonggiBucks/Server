package com.capstone.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtil {

    @Value("${app.upload.dir:${user.home}/uploads/images}")
    private String uploadDir;

    /**
     * 이미지 파일을 저장합니다.
     * 
     * @param file 저장할 이미지 파일
     * @param fileName 저장할 파일명
     * @return 저장된 파일의 웹 접근 경로
     * @throws IOException 파일 저장 중 오류 발생 시
     */
    public String saveImage(MultipartFile file, String fileName) throws IOException {
        // 저장 경로 생성
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 저장
        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());

        // 웹에서 접근 가능한 경로 리턴
        return "/uploads/" + fileName;
    }

    /**
     * 이미지 파일을 삭제합니다.
     * 
     * @param webPath 웹 접근 경로 (/uploads/filename.jpg)
     * @throws IOException 파일 삭제 중 오류 발생 시
     */
    public void deleteImage(String webPath) throws IOException {
        String fileName = webPath.substring(webPath.lastIndexOf("/") + 1);
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
} 