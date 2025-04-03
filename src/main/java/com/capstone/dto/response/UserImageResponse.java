package com.capstone.dto.response;

import com.capstone.domain.ImageAngleType;

public record UserImageResponse(
    Long imageId,
    String imageName,
    String imagePath,
    ImageAngleType angleType
) {
} 