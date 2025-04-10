package com.capstone.service;

import com.capstone.domain.User;
import com.capstone.dto.UserDto;
import com.capstone.dto.response.ResponseLogin;
import com.capstone.exception.DuplicateUserException;
import com.capstone.repository.UserRepository;
import com.capstone.security.UserPrincipal;
import com.capstone.security.UserRole;
import com.capstone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public ResponseLogin authenticate(String loginId,String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // 인증 정보를 가지고, JWT AccessToken 발급
        String accessToken = tokenProvider.createToken(authentication);
        return ResponseLogin.builder()
                .accessToken(accessToken)
                .loginId(userPrincipal.getUsername())
                .build();
    }


    /*
    회원가입
    */
    @Transactional
    public void signUp(String loginId,String rawPassword) {
        validateSignUp(loginId,rawPassword);
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserDto userDto = UserDto.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .role(UserRole.ROLE_CLIENT)
                .build();
        User user = User.of(userDto);
        userRepository.save(user);
    }

    @Transactional
    public void signUpAdminAccount(String loginId,String rawPassword) {
        validateSignUp(loginId,rawPassword);
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserDto userDto = UserDto.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .role(UserRole.ROLE_ADMIN)
                .build();
        User user = User.of(userDto);
        userRepository.save(user);
    }


    private void validateSignUp(String loginId, String rawPassword) {
        boolean isDuplicatedLoginId = userRepository.existsByLoginId(loginId);
        if(isDuplicatedLoginId)
            throw new DuplicateUserException("[ERROR] 이미 존재하는 아이디 입니다.");
        if(rawPassword==null || rawPassword.isBlank())
            throw new IllegalArgumentException("[ERROR] 비밀번호가 빈 문자열입니다.");
    }


}
