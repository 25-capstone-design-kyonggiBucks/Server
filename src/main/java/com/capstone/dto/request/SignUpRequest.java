package com.capstone.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(

        @NotBlank
        String loginId,

        @NotBlank
        String password,

        @NotBlank
        String confirmPassword
) {
}
