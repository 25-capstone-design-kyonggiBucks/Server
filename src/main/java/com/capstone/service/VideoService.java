package com.capstone.service;

import com.capstone.domain.Video;
import com.capstone.domain.VideoType;
import com.capstone.domain.Voice;
import com.capstone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserImageService userImageService;

    /*public void createCustomVideo(Long userId,Long bookId,VideoType videoType) {
        RestClient restClient = RestClient.create();
        restClient.post()
                .uri("url")
                .contentType(MediaType.APPLICATION_JSON)



    }*/

    public Resource getDefaultVideo(Long bookId,VideoType videoType) throws FileNotFoundException {
        if(videoType ==VideoType.CUSTOM)
            throw new IllegalArgumentException("[ERROR] videoType이 custom입니다.");

        Video video = videoRepository.findDefaultVideo(bookId, videoType)
                .orElseThrow(() -> new IllegalStateException("[ERROR] default video를 찾지 못했습니다."));
        return loadVideoResource(video.getVideoPath(),video.getVideoName());
    }
    public Resource getCustomVideo(Long userId,Long bookId, VideoType videoType) throws FileNotFoundException {
        if(videoType==VideoType.DEFAULT)
            throw new IllegalArgumentException("[ERROR] videoType이 default입니다.");
        Video video = videoRepository.findCustomVideo(userId, bookId, videoType)
                .orElseThrow(() -> new IllegalStateException("[ERROR] cusome video를 찾지 못했습니다."));
        return loadVideoResource(video.getVideoPath(),video.getVideoName());
    }
    public ResourceRegion getVideoRegion(Resource video, HttpHeaders headers) throws IOException {
        long contentLength = video.contentLength();

        HttpRange range = headers.getRange().isEmpty() ? null : headers.getRange().get(0);
        long start = range != null ? range.getRangeStart(contentLength) : 0;
        long chunkSize = 1024 * 1024; // 1MB 단위 전송

        long end = range != null ? range.getRangeEnd(contentLength) : Math.min(start + chunkSize - 1, contentLength - 1);
        long rangeLength = end - start + 1;

        return new ResourceRegion(video, start, rangeLength);
    }

    private Resource loadVideoResource(String videoPath, String videoName) throws FileNotFoundException {
        Path path = Paths.get(videoPath).resolve(videoName).normalize();
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Video not found: " + path);
        }
        return new FileSystemResource(path.toFile());
    }
}
