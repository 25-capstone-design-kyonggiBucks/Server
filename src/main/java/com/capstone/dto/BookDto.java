package com.capstone.dto;

import com.capstone.domain.Book;
import com.capstone.domain.BookType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private Long bookId;
    private String title;
    private BookType bookType;
    private String summary;
    private String imagePath;
    private String imageName;

    public static BookDto fromEntity(Book book) {
        return BookDto.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .bookType(book.getBookType())
                .summary(book.getSummary())
                .imagePath(book.getImagePath())
                .imageName(book.getImageName())
                .build();
    }
}
