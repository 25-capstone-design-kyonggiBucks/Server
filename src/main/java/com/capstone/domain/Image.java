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
    private FacialExpression facialExpression;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String imageName;

    void setInfo(String name, String path, FacialExpression expression, User user) {
        this.imageName = name;
        this.imagePath = path;
        this.facialExpression = expression;
        this.user = user;
    }
}
