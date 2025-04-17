package com.capstone.controller;

import com.capstone.common.ApiResponse;
import com.capstone.domain.AudioType;
import com.capstone.dto.AudioDto;
import com.capstone.dto.request.AudioRequest;
import com.capstone.dto.request.AudioUpdateRequest;
import com.capstone.dto.response.AudioResponse;
import com.capstone.security.UserPrincipal;
import com.capstone.service.AudioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/audios")
public class UserAudioController {

    private final AudioService audioService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AudioResponse>> uploadAudio(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestPart("audio") MultipartFile audioFile,
            @RequestPart("request") @Valid AudioRequest request) {
        
        AudioDto audioDto = audioService.uploadAudio(
                userPrincipal.getUsername(),
                audioFile,
                request.description(),
                request.audioType());
        
        AudioResponse response = AudioResponse.from(audioDto);
        ApiResponse<AudioResponse> apiResponse = ApiResponse.success(response, HttpStatus.CREATED);
        
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AudioResponse>>> getAllAudios(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        
        List<AudioDto> audioDtos = audioService.getAllAudios(userPrincipal.getUsername());
        List<AudioResponse> responses = audioDtos.stream()
                .map(AudioResponse::from)
                .collect(Collectors.toList());
        
        ApiResponse<List<AudioResponse>> apiResponse = ApiResponse.success(responses, HttpStatus.OK);
        
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("/{audioId}")
    public ResponseEntity<ApiResponse<AudioResponse>> getAudio(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long audioId) {
        
        AudioDto audioDto = audioService.getAudio(userPrincipal.getUsername(), audioId);
        AudioResponse response = AudioResponse.from(audioDto);
        ApiResponse<AudioResponse> apiResponse = ApiResponse.success(response, HttpStatus.OK);
        
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PutMapping(value = "/{audioId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AudioResponse>> updateAudio(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long audioId,
            @RequestPart(value = "audio", required = false) MultipartFile audioFile,
            @RequestPart("request") @Valid AudioUpdateRequest request) {
        
        AudioDto audioDto = audioService.updateAudio(
                userPrincipal.getUsername(),
                audioId,
                audioFile,
                request.description());
        
        AudioResponse response = AudioResponse.from(audioDto);
        ApiResponse<AudioResponse> apiResponse = ApiResponse.success(response, HttpStatus.OK);
        
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @DeleteMapping("/{audioId}")
    public ResponseEntity<ApiResponse<Void>> deleteAudio(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long audioId) {
        
        audioService.deleteAudio(userPrincipal.getUsername(), audioId);
        ApiResponse<Void> apiResponse = ApiResponse.success(HttpStatus.NO_CONTENT);
        
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }
} 