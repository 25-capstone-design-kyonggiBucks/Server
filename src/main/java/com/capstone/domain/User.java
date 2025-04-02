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

@Entity(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true,nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column
    private UserRole role;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> userImages = new ArrayList<>();


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

    public void addFaceImage(String imageName,String imagePath,ImageAngleType angleType) {
        boolean exists = userImages.stream().anyMatch(i -> i.getImageAngleType() == angleType);

        if(exists)
            throw new IllegalStateException("[ERROR] 이미 등록된 앵글입니다: "+angleType);

        Image image = new Image();
        image.setInfo(imageName,imagePath,angleType,this);
        userImages.add(image);
    }
    public void changeFaceImage(String imageName,String imagePath,ImageAngleType angleType) {
        Image image = userImages.stream().filter(i -> i.getImageAngleType() == angleType).findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당 타입에 해당하는 앵글이 등록되있지 않습니다: " + angleType));
        boolean removeSuccess = userImages.remove(image);
        if(!removeSuccess)
            throw new IllegalStateException("[ERROR] 이미지 삭제에 실패했습니다.");
        addFaceImage(imageName,imagePath,angleType);
    }

}
