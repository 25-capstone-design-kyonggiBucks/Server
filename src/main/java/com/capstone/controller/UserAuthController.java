package com.capstone.controller;

import com.capstone.common.ApiResponse;
import com.capstone.dto.request.SignInRequest;
import com.capstone.dto.request.SignUpRequest;
import com.capstone.dto.response.ResponseLogin;
import com.capstone.exception.BadRequestException;
import com.capstone.exception.InvalidPasswordException;
import com.capstone.security.jwt.CustomJwtFilter;
import com.capstone.service.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    /*
    * accessToken 발급
    *
    */
    @PostMapping("/token")
    public ResponseEntity<ApiResponse<ResponseLogin>> authorize(@Valid @RequestBody SignInRequest signInRequest, Errors errors) {
        errorProcess(errors);
        ResponseLogin token = userAuthService.authenticate(signInRequest.loginId(), signInRequest.password());

        HttpHeaders headers = new HttpHeaders();
        headers.add(CustomJwtFilter.AUTHORIZATION_HEADER,"Bearer " + token.accessToken());
        ApiResponse<ResponseLogin> data = ApiResponse.success(token);
        return ResponseEntity.status(data.getStatus().value())
                .headers(headers)
                .body(data);
    }

    /*
    * 회원 가입 처리
    */
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> signUp(@RequestBody @Valid SignUpRequest signUpRequest,Errors errors) {
        errorProcess(errors);
        validateSignUpPassword(signUpRequest.password(),signUpRequest.confirmPassword());
        userAuthService.signUp(signUpRequest.loginId(),signUpRequest.password());
        ApiResponse<Object> data = ApiResponse.success(HttpStatus.CREATED);
        return ResponseEntity.status(data.getStatus().value())
                .body(data);
    }

    private void errorProcess(Errors errors) {
        if(errors.hasErrors()) {
            throw new BadRequestException(errors.toString());
        }
    }
    private void validateSignUpPassword(String password,String confirmPassword) {
        if(!password.equals(confirmPassword))
            throw new InvalidPasswordException("[ERROR] 비밀번호가 일치하지 않습니다.");
    }


}
