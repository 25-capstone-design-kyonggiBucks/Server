package com.capstone.dto.request;

import com.capstone.domain.Voice;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = false)
public record VoiceRequest(
        @NotNull
        Voice voice
) {
}
