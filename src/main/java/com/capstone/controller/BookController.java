package com.capstone.controller;


import com.capstone.common.ApiResponse;
import com.capstone.domain.BookType;
import com.capstone.dto.BookDto;
import com.capstone.dto.response.BookResponse;
import com.capstone.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksWithBookType(@RequestParam BookType type) {
        List<BookDto> books = bookService.getAllBooksWithType(type);
        List<BookResponse> bookResponses = books.stream().map(BookResponse::from).toList();
        ApiResponse<List<BookResponse>> data = ApiResponse.success(bookResponses, HttpStatus.OK);
        return ResponseEntity.status(data.getStatus())
                .body(data);
    }


    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BookResponse>> getBook(@PathVariable Long bookId) {
        BookDto bookDto = bookService.getBook(bookId);
        BookResponse book = BookResponse.from(bookDto);

        ApiResponse<BookResponse> data = ApiResponse.success(book, HttpStatus.OK);
        return ResponseEntity.status(data.getStatus())
                .body(data);
    }
}
