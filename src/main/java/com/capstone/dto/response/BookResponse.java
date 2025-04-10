package com.capstone.dto.response;

import com.capstone.domain.BookType;
import com.capstone.dto.BookDto;

public record BookResponse(
        Long bookId,
        String title,
        BookType bookType,
        String summary,
        String imageURL
) {
    public static BookResponse from(BookDto bookDto) {
        return new BookResponse(bookDto.getBookId(),bookDto.getTitle(),bookDto.getBookType(),bookDto.getSummary(),bookDto.getImagePath());
    }
}
