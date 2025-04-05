package com.capstone.controller;

import com.capstone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video/")
public class VideoController {

    private final VideoService videoService;

//    @GetMapping("/stream/{fileName}")
//    public ResponseEntity<ResourceRegion> streamVideo(
//            @PathVariable String fileName,
//            @RequestHeader HttpHeaders headers
//    ) throws IOException {
//        Resource video = videoService.getVideoResource(fileName);
//        ResourceRegion region = videoService.getVideoRegion(video, headers);
//
//        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
//                .body(region);
//    }
}
