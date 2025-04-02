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

    }

}
