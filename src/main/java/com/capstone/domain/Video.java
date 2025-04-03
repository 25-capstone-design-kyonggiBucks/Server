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

    @Column(nullable = false)
    private String videoPath;

    @Column(nullable = false)
    private String videoName;


    public static Video of(Book book,String videoPath,String videoName) {
        return Video.of(book,null,videoPath,videoName);
    }

    private static Video of(Book book,User user, String videoPath,String videoName) {
        Video video = new Video();
        video.book = book;
        video.user = user;
        video.videoPath = videoPath;
        video.videoName = videoName;
        return video;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
