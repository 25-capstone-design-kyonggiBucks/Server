package com.capstone.domain;

import com.capstone.dto.BookDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long videoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(value = EnumType.STRING)
    @Column
    private VideoType videoType;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Voice voice;

    @Column(nullable = false)
    private String videoPath;

    @Column(nullable = false)
    private String videoName;



    private static Video of(Book book,User user, String videoPath,String videoName,VideoType videoType,Voice voice) {
        Video video = new Video();
        video.book = book;
        video.user = user;
        video.videoPath = videoPath;
        video.videoName = videoName;
        video.videoType = videoType;
        video.voice = voice;
        return video;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
