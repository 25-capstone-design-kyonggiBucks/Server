package com.capstone.domain;

import com.capstone.dto.BookDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column
    private String storyLine;

    @Column
    private String summary;

    public static Book of(BookDto bookDto) {
        Book book = new Book();
        book.title = bookDto.getTitle();
        book.bookType = bookDto.getBookType();
        book.storyLine = bookDto.getStoryLine();
        book.summary = bookDto.getSummary();
        return book;
    }
}
