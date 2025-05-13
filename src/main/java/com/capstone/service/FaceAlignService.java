package com.capstone.service;

import com.capstone.domain.FacialExpression;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaceAlignService {

    private final RestTemplate restTemplate;

    @Value("${app.python-server.url:http://localhost:5001}")
    private String pythonServerUrl;

    @Value("${app.upload.image}")
    private String imageUploadPath;

    /**
     * 파이썬 서버의 API를 호출하여 얼굴 이미지를 크롭하고 저장합니다.
     *
     * @param imageFile 업로드된 이미지 파일
     * @param expression 표정 타입
     * @return 저장된 이미지의 경로
     */
    public String alignAndCropFace(MultipartFile imageFile, FacialExpression expression) throws IOException {
        String url = pythonServerUrl + "/api/v2/image/align-crop";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 요청 바디 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(imageFile.getBytes()) {
            @Override
            public String getFilename() {
                return imageFile.getOriginalFilename();
            }
        };

        body.add("image", resource);
        body.add("emotion", expression.name());
        body.add("save_path", "uploads/images");

        // HTTP 요청 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // API 호출
        ResponseEntity<FaceAlignResponse> response = restTemplate.postForEntity(
                url, requestEntity, FaceAlignResponse.class);

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null && response.getBody().isSuccess()) {
            log.info("파이썬 서버에서 얼굴 이미지 크롭 성공: {}", response.getBody().getImageUrl());
            return response.getBody().getImageUrl();
        } else {
            log.error("파이썬 서버에서 얼굴 이미지 크롭 실패: {}", response);
            throw new IOException("얼굴 이미지 처리 중 오류가 발생했습니다.");
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FaceAlignResponse {
        private boolean success;
        private String message;
        private String imageUrl;
        private String imagePath;
    }
} 