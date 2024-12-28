package com.rustam.Movie_Website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieUpdateResponse {
    private String title;
    private String director;
    private String releaseYear;
    private String genre;
}