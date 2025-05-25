package com.capstone.service;

import com.capstone.domain.*;
import com.capstone.dto.AudioDto;
import com.capstone.dto.request.CreateCustomVideoRequest;
import com.capstone.dto.response.CreateCustomVideoResponse;
import com.capstone.dto.response.UserImageResponse;
import com.capstone.repository.BookRepository;
import com.capstone.repository.UserRepository;
import com.capstone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final BookRepository bookRepository;
    private final UserImageService userImageService;
    private final AudioService audioService;
    private final UserRepository userRepository;

    private final RestClient restClient;

    @Value("${app.upload.video}")
    private String UPLOADDIR;

    @Value("${app.python-server.url:http://localhost:5001}")
    private String pythonServerUrl;

    @Value("${app.upload.customVideo}")
    private String customVideoUploadPath;


    /* title
    * '금도끼 은도끼'
    * '아낌없이 주는 나무'
    * */
    @Transactional
    public void createCustomVideoWithDefaultVoice(Long userId,Long bookId) {

        String pythonUrl = pythonServerUrl + "/api/custom_video";
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("[ERROR] 유저를 찾을 수 없습니다."));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("[ERROR] 도서를 찾을 수 없습니다."));

        // 표정 이미지 조회 및 유효성 검사
        List<UserImageResponse> userImages = userImageService.getUserImages(user.getLoginId());
        userImageService.ValidateAllEmotionImagesExistInDB(userImages);
        userImageService.validateRequiredEmotionExistInFileSystem(userImages);

        Map<FacialExpression, String > expressionUrlMap = userImages.stream()
                .collect(Collectors.toMap(
                        UserImageResponse::expression,
                        UserImageResponse::imagePath
                ));

        CreateCustomVideoRequest createCustomVideoRequest = CreateCustomVideoRequest.forDefaultVoice(expressionUrlMap,book.getTitle());

        CreateCustomVideoResponse responseDto = restClient.post()
                .uri(pythonUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createCustomVideoRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    String errorBody = response.getBody().toString();
                    throw new IllegalStateException(errorBody);
                }))
                .body(CreateCustomVideoResponse.class);


        Optional<Video> customVideo = videoRepository.findCustomVideo(userId, bookId, VideoType.CUSTOM, Voice.DEFAULT);

        if(customVideo.isPresent()) { // 기존에 커스텀 동영상이 존재할경우
            customVideo.get().changePath(responseDto.videoURL(),responseDto.videoName());

        }else {
            // db 저장
            Video video = Video.of(book, user, responseDto.videoURL(), responseDto.videoName(), VideoType.CUSTOM, Voice.DEFAULT);
            videoRepository.save(video);
        }

    }

    public Resource getDefaultVideo(Long bookId,VideoType videoType) throws FileNotFoundException {
        if(videoType ==VideoType.CUSTOM)
            throw new IllegalArgumentException("[ERROR] videoType이 custom입니다.");

        Video video = videoRepository.findDefaultVideo(bookId, videoType)
                .orElseThrow(() -> new IllegalStateException("[ERROR] default video를 찾지 못했습니다."));
        return loadVideoResource(video.getVideoPath(),video.getVideoName());
    }

    public Resource getCustomVideo(Long userId,Long bookId, VideoType videoType,Voice voice) throws FileNotFoundException {
        if(videoType==VideoType.DEFAULT)
            throw new IllegalArgumentException("[ERROR] videoType이 default입니다.");
        Video video = videoRepository.findCustomVideo(userId, bookId, videoType,voice)
                .orElseThrow(() -> new IllegalStateException("[ERROR] custom video를 찾지 못했습니다."));
        log.info("videoUrl: {}, videoType: {}, voiceType: {}",video.getVideoPath(),video.getVideoType(),video.getVoice());

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
        Path base = Paths.get(UPLOADDIR).normalize();
        Path relative = Paths.get(videoPath).normalize();

        Path commonPrefix = Paths.get("/uploads/videos");
        Path cleanRelative = commonPrefix.relativize(relative);
        Path path = base.resolve(cleanRelative).normalize();
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Video not found: " + path);
        }
        return new FileSystemResource(path.toFile());
    }

   // private CreateCustomVideoRequest buildCustomVideoRequest(List<UserImageResponse> userImages,List<AudioDto>)
}
