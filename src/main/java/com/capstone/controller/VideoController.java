package com.capstone.controller;

import com.capstone.domain.VideoType;
import com.capstone.domain.Voice;
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
@RequestMapping("/api/video/")
public class VideoController {

    private final VideoService videoService;


    @GetMapping("/{bookId}/stream/default")
    public ResponseEntity<ResourceRegion> streamDefaultVideo(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                             @PathVariable Long bookId,
                                                             @RequestHeader HttpHeaders headers) throws IOException {

        Resource video = videoService.getDefaultVideo(bookId, VideoType.DEFAULT);
        ResourceRegion region = videoService.getVideoRegion(video, headers);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }

    @GetMapping("/{bookId}/stream/custom")
    public ResponseEntity<ResourceRegion> streamCustomVideo(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                            @PathVariable Long bookId,
                                                            @RequestParam("voice") Voice voice,
                                                            @RequestHeader HttpHeaders headers) throws IOException {
        Resource video = videoService.getCustomVideo(userPrincipal.getUserId(), bookId, VideoType.CUSTOM, voice);
        ResourceRegion region = videoService.getVideoRegion(video, headers);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }
}
