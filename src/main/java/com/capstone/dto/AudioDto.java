package com.capstone.dto;

import com.capstone.domain.Audio;
import com.capstone.domain.AudioType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AudioDto {
    private Long audioId;
    private Long userId;
    private String audioPath;
    private String audioName;
    private AudioType audioType;
    private String description;

    public static AudioDto from(Audio audio) {
        return AudioDto.builder()
                .audioId(audio.getAudioId())
                .userId(audio.getUser().getUserId())
                .audioPath(audio.getAudioPath())
                .audioName(audio.getAudioName())
                .audioType(audio.getAudioType())
                .description(audio.getDescription())
                .build();
    }
} 