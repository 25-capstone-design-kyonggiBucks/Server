package com.capstone.domain;

import com.capstone.service.VideoService;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;

public enum VideoType {
    DEFAULT {
        @Override
        public Resource resolveVideo(VideoService videoService, Long userId, Long bookId) throws FileNotFoundException {
            return videoService.getDefaultVideo(bookId, this);
        }
    },
    CUSTOM {
        @Override
        public Resource resolveVideo(VideoService videoService, Long userId, Long bookId) throws FileNotFoundException {
            return videoService.getCustomVideo(userId, bookId, this);
        }
    };

    public abstract Resource resolveVideo(VideoService videoService, Long userId, Long bookId) throws FileNotFoundException;
}
