package com.capstone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateBookRequest(

        @NotBlank
        @JsonProperty("bookId") Long bookId,

        @NotBlank
        @JsonProperty("title") String title,

        @NotBlank
        @JsonProperty("summary") String summary
) {
}
