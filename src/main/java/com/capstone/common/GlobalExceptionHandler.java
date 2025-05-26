package com.capstone.common;

import com.capstone.exception.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

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

    @ExceptionHandler({DuplicateUserException.class,InvalidPasswordException.class, InvalidLoginFormatException.class})
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
    @ExceptionHandler(IOException.class)
    public void handleIOException(IOException e, HttpServletResponse response) {
        // 이미 응답이 시작된 경우는 건드리지 않음
        if (response.isCommitted()) {
            return;
        }

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmotionImageNotFoundInDatabaseException.class)
    public ResponseEntity<ApiResponse<?>> handleImageNotFoundInDatabaseException(Exception ex) {
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

    @ExceptionHandler(EmotionImageFileNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleImageNotFoundInFileException(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiResponse<Object> data = ApiResponse.builder()
                .success(false)
                .status(status)
                .message(ex.getMessage())
                .build();
        ex.printStackTrace();
        return ResponseEntity.status(status)
                .body(data);
    }
    @ExceptionHandler({VideoNotFoundInDatabaseException.class, AudioNotFoundInDatabaseException.class})
    public ResponseEntity<?> handleVideoNotFoundInDatabaseException(Exception ex) {
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
    @ExceptionHandler({VideoFileNotFoundException.class,AudioFileNotFoundException.class})
    public ResponseEntity<?> handleVideoFileNotFoundException(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiResponse<Object> data = ApiResponse.builder()
                .success(false)
                .status(status)
                .message(ex.getMessage())
                .build();
        ex.printStackTrace();
        return ResponseEntity.status(status)
                .body(data);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .message("요청 본문 형식이 올바르지 않습니다: " + ex.getMostSpecificCause().getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

}
