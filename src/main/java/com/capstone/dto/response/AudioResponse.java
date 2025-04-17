package com.capstone.dto.response;

import com.capstone.domain.AudioType;
import com.capstone.dto.AudioDto;

public record AudioResponse(
    Long audioId,
    String audioPath,
    String audioName,
    AudioType audioType,
    String description
) {
    public static AudioResponse from(AudioDto audioDto) {
        return new AudioResponse(
            audioDto.getAudioId(),
            audioDto.getAudioPath(),
            audioDto.getAudioName(),
            audioDto.getAudioType(),
            audioDto.getDescription()
        );
    }
} 