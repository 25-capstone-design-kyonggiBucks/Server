package com.capstone.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class VideoStatusGroup {

    private VideoVersionStatus defaultVoice;
    private VideoVersionStatus customVoice;

    public VideoStatusGroup(VideoVersionStatus defaultVoice, VideoVersionStatus customVoice) {
        this.defaultVoice = defaultVoice;
        this.customVoice = customVoice;
    }
}
