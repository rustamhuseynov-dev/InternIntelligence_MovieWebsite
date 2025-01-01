package com.rustam.Movie_Website.controller;

import com.rustam.Movie_Website.dto.request.MovieCreateRequest;
import com.rustam.Movie_Website.dto.request.MovieUpdateRequest;
import com.rustam.Movie_Website.dto.response.MovieCreateResponse;
import com.rustam.Movie_Website.dto.response.MovieDeletedResponse;
import com.rustam.Movie_Website.dto.response.MovieReadResponse;
import com.rustam.Movie_Website.dto.response.MovieUpdateResponse;
import com.rustam.Movie_Website.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping(path = "/create")
    public ResponseEntity<MovieCreateResponse> create(@RequestBody MovieCreateRequest movieCreateRequest){
        return new ResponseEntity<>(movieService.create(movieCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<MovieReadResponse>> read(){
        return new ResponseEntity<>(movieService.read(),HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<MovieUpdateResponse> update(@RequestBody MovieUpdateRequest movieUpdateRequest){
        return new ResponseEntity<>(movieService.update(movieUpdateRequest),HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<MovieDeletedResponse> delete(@PathVariable Long id){
        return new ResponseEntity<>(movieService.delete(id),HttpStatus.ACCEPTED);
    }
}
