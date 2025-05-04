package com.capstone.service;

import com.capstone.domain.Book;
import com.capstone.domain.BookType;
import com.capstone.dto.BookDto;
import com.capstone.repository.BookRepository;
import com.capstone.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final FileUtil fileUtil;


    @Transactional
    public Long addBook(BookDto bookDto, MultipartFile image) {
        Book book = Book.of(bookDto);

        try {
            String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            String imagePath = fileUtil.saveImage(image, imageName);
            book.addBookImage(imagePath,imageName);

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패" + e.getMessage());
        }
        Book savedBook = bookRepository.save(book);
        return savedBook.getBookId();
    }

    @Transactional
    public void updateBook(Long bookId,String title,String summary) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("[ERROR] book을 찾지 못했습니다."));
        book.updateTile(title);
        book.updateSummary(summary);
    }

    /*
    * 추후에 동영상도 같이 삭제되야함
    * */
    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("[ERROR] book을 찾지 못했습니다."));
        try {
            if(book.getImagePath()!=null)
                fileUtil.deleteImage(book.getImagePath());
        } catch (IOException e) {
            throw new RuntimeException("이미지 삭제 실패" + e.getMessage());
        }
        bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(BookDto::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooksWithType(BookType type) {
        return bookRepository.findAllByBookType(type).stream().map(BookDto::fromEntity).toList();
    }


    @Transactional(readOnly = true)
    public BookDto getBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("[ERROR] book을 찾지 못했습니다."));
        return BookDto.fromEntity(book);
    }

}
