package com.rustam.Movie_Website.util;

import com.rustam.Movie_Website.dao.entity.User;
import com.rustam.Movie_Website.dao.repository.UserRepository;
import com.rustam.Movie_Website.dto.TokenPair;
import com.rustam.Movie_Website.exception.custom.InvalidUUIDFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UtilService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user found."));
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
}
