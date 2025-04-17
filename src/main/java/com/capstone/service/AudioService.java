package com.capstone.service;

import com.capstone.domain.Audio;
import com.capstone.domain.AudioType;
import com.capstone.domain.User;
import com.capstone.dto.AudioDto;
import com.capstone.repository.AudioRepository;
import com.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AudioService {

    private final UserRepository userRepository;
    private final AudioRepository audioRepository;
    
    @Value("${app.upload.dir:${user.home}}")
    private String baseUploadDir;
    
    private String getUploadPath() {
        return Paths.get(baseUploadDir, "uploads", "audios").toString();
    }

    public AudioDto uploadAudio(String loginId, MultipartFile audioFile, String description, AudioType audioType) {
        User user = findUserByLoginId(loginId);
        String fileName = storeFile(audioFile);
        String filePath = "uploads/audios/" + fileName;
        
        // description이 null이나 빈 문자열이면 기본값 설정
        String finalDescription = (description == null || description.trim().isEmpty()) 
            ? "audio file" 
            : description;
        
        user.addAudio(fileName, filePath, audioType, finalDescription);
        
        // audios 컬렉션에서 방금 추가된 audio를 찾아서 반환
        Audio savedAudio = user.getAudios().stream()
                .filter(a -> a.getAudioName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 음성 파일 저장에 실패했습니다."));
        
        return AudioDto.from(savedAudio);
    }

    @Transactional(readOnly = true)
    public List<AudioDto> getAllAudios(String loginId) {
        User user = findUserByLoginId(loginId);
        return user.getAudios().stream()
                .map(AudioDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AudioDto getAudio(String loginId, Long audioId) {
        User user = findUserByLoginId(loginId);
        Audio audio = user.getAudios().stream()
                .filter(a -> a.getAudioId().equals(audioId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당 ID의 음성 파일이 존재하지 않습니다: " + audioId));
        
        return AudioDto.from(audio);
    }

    public AudioDto updateAudio(String loginId, Long audioId, MultipartFile audioFile, String description) {
        User user = findUserByLoginId(loginId);
        
        // 기존 오디오 정보 찾기
        Audio existingAudio = user.getAudios().stream()
                .filter(a -> a.getAudioId().equals(audioId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당 ID의 음성 파일이 존재하지 않습니다: " + audioId));
        
        // 새 파일이 있으면 기존 파일 삭제 후 새 파일 저장
        String fileName = existingAudio.getAudioName();
        String filePath = existingAudio.getAudioPath();
        
        if (audioFile != null && !audioFile.isEmpty()) {
            deleteExistingFile(fileName);
            fileName = storeFile(audioFile);
            filePath = "uploads/audios/" + fileName;
        }
        
        // description이 null이나 빈 문자열이면 기본값 설정
        String finalDescription = (description == null || description.trim().isEmpty()) 
            ? "updated audio file" 
            : description;
        
        user.updateAudio(audioId, fileName, filePath, finalDescription);
        
        return getAudio(loginId, audioId);
    }

    public void deleteAudio(String loginId, Long audioId) {
        User user = findUserByLoginId(loginId);
        
        // 삭제할 오디오 찾기
        Audio audioToDelete = user.getAudios().stream()
                .filter(a -> a.getAudioId().equals(audioId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당 ID의 음성 파일이 존재하지 않습니다: " + audioId));
        
        // 파일 시스템에서 삭제
        deleteExistingFile(audioToDelete.getAudioName());
        
        // 사용자 엔티티에서 삭제
        user.removeAudio(audioId);
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalStateException("[ERROR] 존재하지 않는 사용자입니다: " + loginId));
    }

    private String storeFile(MultipartFile file) {
        try {
            // 업로드 디렉토리가 없으면 생성
            Path uploadPath = Paths.get(getUploadPath());
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // 파일명 중복 방지를 위해 UUID 사용
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + extension;
            
            // 파일 저장
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            
            return fileName;
        } catch (IOException ex) {
            throw new IllegalStateException("[ERROR] 파일 저장에 실패했습니다: " + ex.getMessage());
        }
    }

    private void deleteExistingFile(String fileName) {
        try {
            Path filePath = Paths.get(getUploadPath()).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new IllegalStateException("[ERROR] 파일 삭제에 실패했습니다: " + ex.getMessage());
        }
    }
} 