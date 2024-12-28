package com.rustam.Movie_Website.mapper;

import com.rustam.Movie_Website.dao.entity.Movie;
import com.rustam.Movie_Website.dto.request.MovieCreateRequest;
import com.rustam.Movie_Website.dto.response.MovieCreateResponse;
import com.rustam.Movie_Website.dto.response.MovieReadResponse;
import com.rustam.Movie_Website.dto.response.MovieUpdateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MovieMapper {

    MovieUpdateResponse toUpdated(Movie movie);

    Movie toSave(MovieCreateRequest movieCreateRequest);

    MovieCreateResponse toResponse(Movie movie);

    List<MovieReadResponse> toResponses(List<Movie> movies);
}
