package com.capstone.dto.request;

import com.capstone.domain.BookType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CreateBookRequest(
        @NotBlank
        @JsonProperty("title") String title,

        @NotBlank
        @JsonProperty("bookType") BookType bookType,

        @NotBlank
        @JsonProperty("summary") String summary
) {
}
