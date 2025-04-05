package com.capstone.controller;

import com.capstone.domain.VideoType;
import com.capstone.security.UserPrincipal;
import com.capstone.service.VideoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/video/")
public class VideoController {

    private final VideoService videoService;


    @GetMapping("/{bookId}/stream")
    public ResponseEntity<ResourceRegion> streamDefaultVideo(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                             @PathVariable Long bookId,
                                                             @RequestHeader HttpHeaders headers,
                                                             @RequestParam VideoType type) throws IOException {
        if(type !=VideoType.CUSTOM && type!=VideoType.DEFAULT)
            throw new IllegalStateException("[ERROR] videoType이 올바르지 않습니다.");
        Resource video = type.resolveVideo(videoService, userPrincipal.getUserId(), bookId);
        ResourceRegion region = videoService.getVideoRegion(video, headers);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }
}
