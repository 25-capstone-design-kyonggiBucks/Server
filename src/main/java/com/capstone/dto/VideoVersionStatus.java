package com.capstone.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class VideoVersionStatus {
    private boolean dbExists;
    private boolean fileExists;
    private boolean playable;

    public VideoVersionStatus(boolean dbExists, boolean playable, boolean fileExists) {
        this.dbExists = dbExists;
        this.playable = playable;
        this.fileExists = fileExists;
    }
}
