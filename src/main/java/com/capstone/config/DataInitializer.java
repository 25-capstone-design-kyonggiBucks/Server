package com.capstone.config;

import com.capstone.domain.Book;
import com.capstone.domain.BookType;
import com.capstone.domain.User;
import com.capstone.domain.Video;
import com.capstone.dto.BookDto;
import com.capstone.dto.UserDto;
import com.capstone.repository.BookRepository;
import com.capstone.repository.UserRepository;
import com.capstone.repository.VideoRepository;
import com.capstone.security.UserRole;
import com.capstone.service.UserAuthService;
import com.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    @Value("${app.upload.video}")
    private String uploadDir;

    private final BookRepository bookRepository;
    private final VideoRepository videoRepository;
    private final UserAuthService userAuthService;


    @Override
    public void run(String... args) throws Exception {
        BookDto bookDto = BookDto.builder()
                .title("toystory")
                .bookType(BookType.NONE)
                .summary("summary")
                .build();
        Book book = Book.of(bookDto);
        bookRepository.save(book);

        Video video = Video.of(book, uploadDir + "/default", "toystory.mp4");
        videoRepository.save(video);
        userAuthService.signUpAdminAccount("admin","1234");
    }
}
