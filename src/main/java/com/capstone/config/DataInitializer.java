package com.capstone.config;

import com.capstone.domain.Book;
import com.capstone.domain.BookType;
import com.capstone.domain.Video;
import com.capstone.dto.BookDto;
import com.capstone.repository.BookRepository;
import com.capstone.repository.VideoRepository;
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


    @Override
    public void run(String... args) throws Exception {
        BookDto bookDto = BookDto.builder()
                .title("toystory")
                .bookType(BookType.FOLKTALE)
                .storyLine("sotry")
                .summary("summary")
                .build();
        Book book = Book.of(bookDto);
        bookRepository.save(book);

        Video video = Video.of(book, uploadDir + "/default", "toystory.mp4");
        videoRepository.save(video);
    }
}
