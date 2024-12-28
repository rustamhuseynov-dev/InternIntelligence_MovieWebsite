package com.rustam.Movie_Website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieUpdateRequest {
    private String title;
    private String director;
    private String releaseYear;
    private String genre;
}
