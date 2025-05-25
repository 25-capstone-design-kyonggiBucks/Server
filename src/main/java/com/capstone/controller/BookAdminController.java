package com.capstone.controller;

import com.capstone.common.ApiResponse;
import com.capstone.domain.BookType;
import com.capstone.dto.BookDto;
import com.capstone.dto.request.CreateBookRequest;
import com.capstone.dto.request.UpdateBookRequest;
import com.capstone.dto.response.BookResponse;
import com.capstone.dto.response.CreateBookResponse;
import com.capstone.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/admin/books")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class BookAdminController {

    private final BookService bookService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Object>> addBook(@RequestPart("book") CreateBookRequest request,
                                                       @RequestPart("image") MultipartFile image) {
        BookDto bookDto = BookDto.builder()
                .title(request.title())
                .bookType(request.bookType())
                .summary(request.summary())
                .build();

        Long bookId = bookService.addBook(bookDto, image);
        CreateBookResponse response = CreateBookResponse.of(bookId);
        ApiResponse<Object> data = ApiResponse.success(response,HttpStatus.CREATED);
        return ResponseEntity.status(data.getStatus())
                .body(data);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Object>> updateBook(@RequestBody UpdateBookRequest request) {
        bookService.updateBook(request.bookId(),request.title(),request.summary());
        ApiResponse<Object> data = ApiResponse.success(HttpStatus.OK);
        return ResponseEntity.status(data.getStatus())
                .body(data);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Object>> deleteBook(@RequestParam("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        ApiResponse<Object> data = ApiResponse.success(HttpStatus.OK);
        return ResponseEntity.status(data.getStatus())
                .body(data);
    }

}
