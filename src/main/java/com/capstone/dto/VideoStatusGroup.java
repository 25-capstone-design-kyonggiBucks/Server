package com.capstone.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class VideoStatusGroup {

    private Long bookId;
    private VideoVersionStatus defaultVoice;
    private VideoVersionStatus customVoice;

    public VideoStatusGroup(Long bookId, VideoVersionStatus defaultVoice, VideoVersionStatus customVoice) {
        this.bookId = bookId;
        this.defaultVoice = defaultVoice;
        this.customVoice = customVoice;
    }
}
