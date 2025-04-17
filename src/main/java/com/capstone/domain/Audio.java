package com.capstone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Audio {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audio_id")
    private Long audioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String audioPath;

    @Column(nullable = false)
    private String audioName;

    @Enumerated(value = EnumType.STRING)
    @Column
    private AudioType audioType;

    @Column
    private String description;

    public static Audio of(User user, String audioPath, String audioName, AudioType audioType, String description) {
        Audio audio = new Audio();
        audio.user = user;
        audio.audioPath = audioPath;
        audio.audioName = audioName;
        audio.audioType = audioType;
        audio.description = description;
        return audio;
    }

    public void updateAudio(String audioPath, String audioName) {
        this.audioPath = audioPath;
        this.audioName = audioName;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
} 