package com.capstone.dto.response;

public record CreateBookResponse(

        Long bookId
) {
    public static CreateBookResponse of(Long bookId) {
        return new CreateBookResponse(bookId);
    }
}
