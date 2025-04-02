package com.capstone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long image_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ImageAngleType imageAngleType;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String imageName;


//    public static Image of(String imagePath,String imageName,ImageAngleType angleType) {
//        Image image = new Image();
//        image.u
//    }
}
