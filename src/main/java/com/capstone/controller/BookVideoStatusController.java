package com.capstone.controller;


import com.capstone.common.ApiResponse;
import com.capstone.dto.VideoStatusGroup;
import com.capstone.security.UserPrincipal;
import com.capstone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/videos/")
public class BookVideoStatusController {

    private final VideoService videoService;

    @GetMapping("/{bookId}/status")
    public ResponseEntity<ApiResponse<VideoStatusGroup>> getVideoStatus(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                              @PathVariable("bookId") Long bookId) {

        VideoStatusGroup videoStatus = videoService.getVideoStatusByBookId(userPrincipal.getUserId(), bookId);

        ApiResponse<VideoStatusGroup> data = ApiResponse.success(videoStatus, HttpStatus.OK);
        return ResponseEntity.status(data.getStatus())
                .body(data);

    }

}
