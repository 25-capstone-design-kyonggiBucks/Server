package com.capstone.service;

import com.capstone.domain.Image;
import com.capstone.domain.ImageAngleType;
import com.capstone.domain.User;
import com.capstone.dto.response.UserImageResponse;
import com.capstone.exception.ResourceNotFoundException;
import com.capstone.repository.UserRepository;
import com.capstone.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserImageService {

    private final UserRepository userRepository;
    private final FileUtil fileUtil;

    @Transactional
    public void uploadImage(String loginId, MultipartFile image, ImageAngleType angleType) {
        User user = getUserByLoginId(loginId);
        
        try {
            // 이미지 저장
            String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            String imagePath = fileUtil.saveImage(image, imageName);
            
            // 사용자에게 이미지 추가
            user.addFaceImage(imageName, imagePath, angleType);
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패: " + e.getMessage());
        }
    }

    @Transactional
    public void updateImage(String loginId, MultipartFile image, ImageAngleType angleType) {
        User user = getUserByLoginId(loginId);
        
        try {
            // 이미지 저장
            String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            String imagePath = fileUtil.saveImage(image, imageName);
            
            // 기존 이미지 찾기 및 삭제
            user.getUserImages().stream()
                    .filter(img -> img.getImageAngleType() == angleType)
                    .findFirst()
                    .ifPresent(img -> {
                        try {
                            fileUtil.deleteImage(img.getImagePath());
                        } catch (IOException e) {
                            throw new RuntimeException("이미지 삭제 실패: " + e.getMessage());
                        }
                    });
            
            // 사용자에게 이미지 업데이트
            user.changeFaceImage(imageName, imagePath, angleType);
        } catch (IOException e) {
            throw new RuntimeException("이미지 업데이트 실패: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteImage(String loginId, ImageAngleType angleType) {
        User user = getUserByLoginId(loginId);
        
        // 기존 이미지 찾기 및 삭제
        user.getUserImages().stream()
                .filter(img -> img.getImageAngleType() == angleType)
                .findFirst()
                .ifPresent(img -> {
                    try {
                        fileUtil.deleteImage(img.getImagePath());
                        user.getUserImages().remove(img);
                    } catch (IOException e) {
                        throw new RuntimeException("이미지 삭제 실패: " + e.getMessage());
                    }
                });
    }

    @Transactional(readOnly = true)
    public List<UserImageResponse> getUserImages(String loginId) {
        User user = getUserByLoginId(loginId);
        
        return user.getUserImages().stream()
                .map(image -> new UserImageResponse(
                        image.getImage_id(),
                        image.getImageName(),
                        image.getImagePath(),
                        image.getImageAngleType()))
                .collect(Collectors.toList());
    }

    private User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다: " + loginId));
    }
} 