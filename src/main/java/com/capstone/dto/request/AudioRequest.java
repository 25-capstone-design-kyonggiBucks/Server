package com.capstone.dto.request;

import com.capstone.domain.AudioType;
import jakarta.validation.constraints.NotNull;

public record AudioRequest(
    String description,
    
    @NotNull(message = "오디오 타입은 필수값입니다.")
    AudioType audioType
) {
} 