package com.rustam.Movie_Website.service;

import com.rustam.Movie_Website.dto.request.MovieCreateRequest;
import com.rustam.Movie_Website.dto.request.MovieUpdateRequest;
import com.rustam.Movie_Website.dto.response.MovieCreateResponse;
import com.rustam.Movie_Website.dto.response.MovieDeletedResponse;
import com.rustam.Movie_Website.dto.response.MovieReadResponse;
import com.rustam.Movie_Website.dto.response.MovieUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    
    public MovieCreateResponse create(MovieCreateRequest movieCreateRequest) {
    }

    public MovieReadResponse read() {
    }

    public MovieUpdateResponse update(MovieUpdateRequest movieUpdateRequest) {
    }

    public MovieDeletedResponse delete(Long id) {
    }
}
