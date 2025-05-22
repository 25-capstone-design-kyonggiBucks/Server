package com.capstone.domain;

import com.capstone.dto.UserDto;
import com.capstone.security.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id", unique = true,nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column
    private UserRole role;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> userImages = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Audio> audios = new ArrayList<>();

    public static User of(UserDto userDto) {
        User user = new User();
        user.loginId = userDto.getLoginId();
        user.password = userDto.getPassword();
        user.role = userDto.getRole();
        return user;
    }

    public void changePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        if(rawPassword==null || rawPassword.isBlank()) {
            throw new IllegalStateException("[ERROR] 비밀번호의 입력이 비어있습니다.");
        }else if(rawPassword.length() < 4)
            throw new IllegalStateException("[ERROR] 비밀번호는 최소 4자 이상이어야 합니다.");
        this.password = passwordEncoder.encode(rawPassword);
    }

    public void addFaceImage(String imageName, String imagePath, FacialExpression expression) {
        boolean exists = userImages.stream()
                .anyMatch(i -> i.getFacialExpression() == expression);

        if(exists)
            throw new IllegalStateException("[ERROR] 이미 등록된 표정입니다: " + expression);

        Image image = new Image();
        image.setInfo(imageName, imagePath, expression, this);
        userImages.add(image);
    }

    public void changeFaceImage(String imageName, String imagePath, FacialExpression expression) {
        Image image = userImages.stream()
                .filter(i -> i.getFacialExpression() == expression)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당 표정에 해당하는 이미지가 등록되어 있지 않습니다: " + expression));
        boolean removeSuccess = userImages.remove(image);
        if(!removeSuccess)
            throw new IllegalStateException("[ERROR] 이미지 삭제에 실패했습니다.");
        addFaceImage(imageName, imagePath, expression);
    }

    public void addAudio(String audioName, String audioPath, AudioType audioType, String description) {
        Audio audio = Audio.of(this, audioPath, audioName, audioType, description);
        audios.add(audio);
    }

    public void updateAudio(Long audioId, String audioName, String audioPath, String description) {
        Audio audio = audios.stream()
                .filter(a -> a.getAudioId().equals(audioId))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당 ID의 음성 파일이 존재하지 않습니다: " + audioId));
        
        audio.updateAudio(audioPath, audioName);
        audio.updateDescription(description);
    }

    public void removeAudio(Long audioId) {
        Audio audio = audios.stream()
                .filter(a -> a.getAudioId().equals(audioId))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당 ID의 음성 파일이 존재하지 않습니다: " + audioId));
        
        boolean removed = audios.remove(audio);
        if (!removed) {
            throw new IllegalStateException("[ERROR] 음성 파일 삭제에 실패했습니다.");
        }
    }
}
