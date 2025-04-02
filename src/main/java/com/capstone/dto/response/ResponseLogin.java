package com.capstone.dto.response;

import lombok.Builder;

@Builder
public record ResponseLogin(
        String loginId,
        String accessToken
) {
}
