package com.capstone.dto.response;

import com.capstone.domain.FacialExpression;

public record UserImageResponse(
    Long imageId,
    String imageName,
    String imagePath,
    FacialExpression expression
) {
} 