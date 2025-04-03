package com.capstone.controller;

import com.capstone.common.ApiResponse;
import com.capstone.domain.ImageAngleType;
import com.capstone.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user/images")
@RequiredArgsConstructor
public class UserImageController {

    private final UserImageService userImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Object>> uploadImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("image") MultipartFile image,
            @RequestParam("angleType") ImageAngleType angleType) {
        
        userImageService.uploadImage(userDetails.getUsername(), image, angleType);
        ApiResponse<Object> response = ApiResponse.success(HttpStatus.CREATED);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Object>> updateImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("image") MultipartFile image,
            @RequestParam("angleType") ImageAngleType angleType) {
        
        userImageService.updateImage(userDetails.getUsername(), image, angleType);
        ApiResponse<Object> response = ApiResponse.success(HttpStatus.OK);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Object>> deleteImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("angleType") ImageAngleType angleType) {
        
        userImageService.deleteImage(userDetails.getUsername(), angleType);
        ApiResponse<Object> response = ApiResponse.success(HttpStatus.OK);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getUserImages(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        var images = userImageService.getUserImages(userDetails.getUsername());
        ApiResponse<Object> response = ApiResponse.success(images);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }
} 