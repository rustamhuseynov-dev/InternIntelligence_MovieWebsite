package com.rustam.Movie_Website.service;

import com.rustam.Movie_Website.dao.entity.Movie;
import com.rustam.Movie_Website.dao.entity.User;
import com.rustam.Movie_Website.dao.repository.MovieRepository;
import com.rustam.Movie_Website.dto.request.MovieCreateRequest;
import com.rustam.Movie_Website.dto.request.MovieUpdateRequest;
import com.rustam.Movie_Website.dto.response.MovieCreateResponse;
import com.rustam.Movie_Website.dto.response.MovieDeletedResponse;
import com.rustam.Movie_Website.dto.response.MovieReadResponse;
import com.rustam.Movie_Website.dto.response.MovieUpdateResponse;
import com.rustam.Movie_Website.exception.custom.ExistsException;
import com.rustam.Movie_Website.mapper.MovieMapper;
import com.rustam.Movie_Website.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final UtilService utilService;
    private final ModelMapper modelMapper;
    
    public MovieCreateResponse create(MovieCreateRequest movieCreateRequest) {
        Movie movie = movieMapper.toSave(movieCreateRequest);
        movieRepository.save(movie);
        return movieMapper.toResponse(movie);
    }

    public List<MovieReadResponse> read() {
        List<Movie> movies = utilService.movieFindAll();
        return movieMapper.toResponses(movies);
    }

    public MovieUpdateResponse update(MovieUpdateRequest movieUpdateRequest) {
        Movie movie = utilService.movieFindById(movieUpdateRequest.getId());
        boolean exists = utilService.movieFindAll().stream()
                .map(Movie::getTitle)
                .anyMatch(existingUsername -> existingUsername.equals(movieUpdateRequest.getTitle()));
        if (exists){
            throw new ExistsException("This movie title is already taken.");
        }
        modelMapper.map(movieUpdateRequest,movie);
        movieRepository.save(movie);
        return movieMapper.toUpdated(movie);
    }

    public MovieDeletedResponse delete(Long id) {
        Movie movie = utilService.movieFindById(id);
        MovieDeletedResponse deletedResponse = new MovieDeletedResponse();
        modelMapper.map(movie,deletedResponse);
        deletedResponse.setText("This movie was deleted by you.");
        movieRepository.delete(movie);
        return deletedResponse;
    }
}
