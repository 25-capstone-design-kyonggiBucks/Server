package com.capstone.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record CreateCustomVideoRequest(

        /*@NotNull
        @JsonProperty("happyURL")
        String happyImageURL,

        @NotNull
        @JsonProperty("sadImageURL")
        String sadImageURL,

        @NotNull
        @JsonProperty("surprisedURL")
        String surprisedImageURL,

        @NotNull
        @JsonProperty("angryURL")
        String angryImageURL*/
) {
}
