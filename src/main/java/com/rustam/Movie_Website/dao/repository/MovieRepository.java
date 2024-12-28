package com.rustam.Movie_Website.dao.repository;

import com.rustam.Movie_Website.dao.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
