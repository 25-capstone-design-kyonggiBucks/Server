package com.capstone.common;

import com.capstone.exception.BadRequestException;
import com.capstone.exception.DuplicateUserException;
import com.capstone.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneralException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse<Object> data = ApiResponse.builder()
                .success(false)
                .status(status)
                .message(ex.getMessage())
                .build();
        ex.printStackTrace();
        return ResponseEntity.status(status)
                .body(data);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiResponse<Object> data = ApiResponse.builder()
                .success(false)
                .status(status)
                .message(ex.getMessage())
                .build();
        ex.printStackTrace();
        return ResponseEntity.status(status)
                .body(data);
    }
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiResponse<?>> handleSignInException(Exception ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiResponse<Object> data = ApiResponse.builder()
                .success(false)
                .status(status)
                .message(ex.getMessage())
                .build();
        ex.printStackTrace();
        return ResponseEntity.status(status)
                .body(data);
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthorizationDeniedException(Exception ex) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiResponse<Object> data = ApiResponse.builder()
                .success(false)
                .status(status)
                .message(ex.getMessage())
                .build();
        ex.printStackTrace();
        return ResponseEntity.status(status)
                .body(data);
    }

    @ExceptionHandler({DuplicateUserException.class,InvalidPasswordException.class})
    public ResponseEntity<ApiResponse<?>> handleSignUpException(Exception ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiResponse<Object> data = ApiResponse.builder()
                .success(false)
                .status(status)
                .message(ex.getMessage())
                .build();
        ex.printStackTrace();
        return ResponseEntity.status(status)
                .body(data);
    }

}
