package com.capstone.common;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ApiResponse<T> {
    private boolean success;
    private HttpStatus status;
    private String message;
    private T data;


    public static <T> ApiResponse<T> success(HttpStatus status) {
        return success(null,status);
    }
    public static <T> ApiResponse<T> success(T data) {
        return success(data,HttpStatus.OK);
    }
    public static <T> ApiResponse<T> success(T data,HttpStatus status) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(status)
                .data(data)
                .build();
    }
}
