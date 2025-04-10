package com.capstone.domain;

import com.capstone.dto.BookDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BookType bookType;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Video> videos = new ArrayList<>();

    @Column
    private String imagePath;

    @Column
    private String imageName;

    @Column
    private String summary;

    public static Book of(BookDto bookDto) {
        Book book = new Book();
        book.title = bookDto.getTitle();
        book.bookType = bookDto.getBookType();
        book.summary = bookDto.getSummary();
        book.imagePath = bookDto.getImagePath();
        book.imageName = bookDto.getImageName();
        return book;
    }

    public void updateTile(String title) {
        if(title==null || title.isEmpty())
            throw new IllegalStateException("[ERROR] title 입력이 비었습니다.");
        this.title = title;
    }
    public void updateSummary(String summary) {
        if(summary==null || summary.isEmpty())
            throw new IllegalStateException("[ERROR] summary 입력이 비었습니다.");
        this.summary = summary;
    }
    public void addBookImage(String imagePath,String imageName) {
        this.imagePath = imagePath;
        this.imageName = imageName;
    }
    public void deleteBookImage() {
        this.imagePath = null;
        this.imageName = null;
    }
}
