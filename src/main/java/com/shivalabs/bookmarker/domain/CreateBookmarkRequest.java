package com.shivalabs.bookmarker.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateBookmarkRequest {
    @NotEmpty(message = "Title should not be empty")
    private String title;
    @NotEmpty(message = "Url should not be empty")
    private String url;

}
