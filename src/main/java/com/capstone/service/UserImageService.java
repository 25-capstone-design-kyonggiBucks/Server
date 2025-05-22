package com.capstone.service;

import com.capstone.domain.FacialExpression;
import com.capstone.domain.User;
import com.capstone.dto.response.UserImageResponse;
import com.capstone.exception.EmotionImageFileNotFoundException;
import com.capstone.exception.EmotionImageNotFoundInDatabaseException;
import com.capstone.exception.ResourceNotFoundException;
import com.capstone.repository.UserRepository;
import com.capstone.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserImageService {

    private final UserRepository userRepository;
    private final FileUtil fileUtil;
    private final FaceAlignService faceAlignService;

    @Transactional
    public void uploadImage(String loginId, MultipartFile image, FacialExpression expression) {
        User user = getUserByLoginId(loginId);
        
        try {
            // 파이썬 API를 사용하여 얼굴 인식 및 크롭 처리
            String imagePath = faceAlignService.alignAndCropFace(image, expression);
            
            // 파일 이름 추출
            String imageName = imagePath.substring(imagePath.lastIndexOf('/') + 1);
            
            // 사용자에게 이미지 추가
            user.addFaceImage(imageName, imagePath, expression);
            log.info("사용자 {} 얼굴 이미지({}) 업로드 완료", loginId, expression);
        } catch (IOException e) {
            log.error("이미지 업로드 실패: {}", e.getMessage(), e);
            throw new RuntimeException("이미지 업로드 실패: " + e.getMessage());
        }
    }

    @Transactional
    public void updateImage(String loginId, MultipartFile image, FacialExpression expression) {
        User user = getUserByLoginId(loginId);
        
        try {
            // 기존 이미지 찾기 및 삭제
            user.getUserImages().stream()
                    .filter(img -> img.getFacialExpression() == expression)
                    .findFirst()
                    .ifPresent(img -> {
                        try {
                            fileUtil.deleteImage(img.getImagePath());
                        } catch (IOException e) {
                            throw new RuntimeException("이미지 삭제 실패: " + e.getMessage());
                        }
                    });
            
            // 파이썬 API를 사용하여 얼굴 인식 및 크롭 처리
            String imagePath = faceAlignService.alignAndCropFace(image, expression);
            
            // 파일 이름 추출
            String imageName = imagePath.substring(imagePath.lastIndexOf('/') + 1);
            
            // 사용자에게 이미지 업데이트
            user.changeFaceImage(imageName, imagePath, expression);
            log.info("사용자 {} 얼굴 이미지({}) 업데이트 완료", loginId, expression);
        } catch (IOException e) {
            log.error("이미지 업데이트 실패: {}", e.getMessage(), e);
            throw new RuntimeException("이미지 업데이트 실패: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteImage(String loginId, FacialExpression expression) {
        User user = getUserByLoginId(loginId);
        
        // 기존 이미지 찾기 및 삭제
        user.getUserImages().stream()
                .filter(img -> img.getFacialExpression() == expression)
                .findFirst()
                .ifPresent(img -> {
                    try {
                        fileUtil.deleteImage(img.getImagePath());
                        user.getUserImages().remove(img);
                        log.info("사용자 {} 얼굴 이미지({}) 삭제 완료", loginId, expression);
                    } catch (IOException e) {
                        log.error("이미지 삭제 실패: {}", e.getMessage(), e);
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
                        image.getFacialExpression()))
                .collect(Collectors.toList());
    }


    private User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다: " + loginId));
    }

    public void ValidateAllEmotionImagesExistInDB(List<UserImageResponse> images) {
        List<FacialExpression> userEmotions = images.stream().map(UserImageResponse::expression).toList();

        // 필수 표정 목록
        List<FacialExpression> requiredExpression = List.of(FacialExpression.HAPPY, FacialExpression.ANGRY, FacialExpression.SAD, FacialExpression.SURPRISED);

        HashSet<FacialExpression> userEmotionSet = new HashSet<>(userEmotions);

        List<FacialExpression> missing= requiredExpression.stream().filter(expression -> !userEmotionSet.contains(expression)).toList();

        if(!missing.isEmpty())
            throw new EmotionImageNotFoundInDatabaseException("[ERROR] 다음 표정이 누락됐습니다." + missing);
    }
    public void validateRequiredEmotionExistInFileSystem(List<UserImageResponse> images) {
        List<FacialExpression> missing = new ArrayList<>();

        for (UserImageResponse image : images) {
            String path = image.imagePath();
            FacialExpression expression = image.expression();

            if(!Files.exists(Path.of(path))) {
                missing.add(image.expression());
            }
        }
        if(!missing.isEmpty()) {
            throw new EmotionImageFileNotFoundException("[ERROR] 다음 표정이 파일 시스템에 존재하지 않습니다." + missing);
        }

    }
}