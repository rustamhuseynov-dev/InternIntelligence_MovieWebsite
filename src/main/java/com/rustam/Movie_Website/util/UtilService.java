package com.rustam.Movie_Website.util;

import com.rustam.Movie_Website.dao.entity.Movie;
import com.rustam.Movie_Website.dao.entity.User;
import com.rustam.Movie_Website.dao.repository.MovieRepository;
import com.rustam.Movie_Website.dao.repository.UserRepository;
import com.rustam.Movie_Website.dto.TokenPair;
import com.rustam.Movie_Website.dto.request.RefreshRequest;
import com.rustam.Movie_Website.exception.custom.InvalidUUIDFormatException;
import com.rustam.Movie_Website.exception.custom.MovieNotFoundException;
import com.rustam.Movie_Website.exception.custom.UnauthorizedException;
import com.rustam.Movie_Website.exception.custom.UserNotFoundException;
import com.rustam.Movie_Website.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UtilService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final MovieRepository movieRepository;
    private final RedisTemplate<String,String> redisTemplate;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such username found."));
    }

    public UUID convertToUUID(String id) {
        try {
            log.info("id {}",id);
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDFormatException("Invalid UUID format for ID: " + id, e);
        }
    }

    public TokenPair tokenProvider(String id, UserDetails userDetails) {
        return userDetails.isEnabled() ?
                TokenPair.builder()
                        .accessToken(jwtUtil.createToken(String.valueOf(id)))
                        .refreshToken(jwtUtil.createRefreshToken(String.valueOf(id)))
                        .build()
                : new TokenPair();
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No such user found."));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public String refreshToken(RefreshRequest request) {
        System.out.println(request.getRefreshToken());
        log.info("api-gateway to send refresh token {}",request.getRefreshToken());
        String userId = jwtUtil.getUserIdAsUsernameFromTokenExpired(request.getRefreshToken()); // Token-dan user ID-ni çıxart
        if (userId == null) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        String redisKey = "refresh_token:" + userId; // Redis açarını yaradın
        String storedRefreshToken = redisTemplate.opsForValue().get(redisKey); // Redis-dən saxlanmış refresh token-i alın
        if (storedRefreshToken != null) {
            return jwtUtil.createToken(userId);
        } else {
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

    public List<Movie> movieFindAll() {
        return movieRepository.findAll();
    }

    public Movie movieFindById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("No such movie found."));
    }
}
