package com.capstone.dto;

import com.capstone.domain.BookType;
import lombok.Data;

@Data
public class BookDto {

    private String title;
    private BookType bookType;
    private String storyLine;
    private String summary;
}
