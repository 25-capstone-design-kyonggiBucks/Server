package com.capstone.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class VideoService {

//    private static final String VIDEO_DIR = "videos/"; // 실무에선 @Value로 주입 권장
//
//    public Resource getVideoResource(String fileName) throws IOException {
//        Path path = Paths.get(VIDEO_DIR + fileName);
//        if (!Files.exists(path)) {
//            throw new FileNotFoundException("Video not found: " + fileName);
//        }
//        return new FileSystemResource(path.toFile());
//    }
//
//    public ResourceRegion getVideoRegion(Resource video, HttpHeaders headers) throws IOException {
//        long contentLength = video.contentLength();
//
//        HttpRange range = headers.getRange().isEmpty() ? null : headers.getRange().get(0);
//        long start = range != null ? range.getRangeStart(contentLength) : 0;
//        long chunkSize = 1024 * 1024; // 1MB 단위 전송
//
//        long end = Math.min(chunkSize, contentLength - start);
//        return new ResourceRegion(video, start, end);
//    }

}
