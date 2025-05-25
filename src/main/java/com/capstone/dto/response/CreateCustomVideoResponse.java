package com.capstone.dto.response;

import com.capstone.domain.VideoType;
import com.capstone.domain.Voice;

import java.time.LocalDateTime;

public record CreateCustomVideoResponse(
        String videoName,
        String videoURL
) {
}
