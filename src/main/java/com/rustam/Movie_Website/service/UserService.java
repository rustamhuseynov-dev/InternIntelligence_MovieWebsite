package com.rustam.Movie_Website.service;

import com.rustam.Movie_Website.dao.entity.User;
import com.rustam.Movie_Website.dao.repository.UserRepository;
import com.rustam.Movie_Website.dto.TokenPair;
import com.rustam.Movie_Website.dto.request.AuthRequest;
import com.rustam.Movie_Website.dto.request.UserRegisterRequest;
import com.rustam.Movie_Website.dto.response.AuthResponse;
import com.rustam.Movie_Website.dto.response.UserRegisterResponse;
import com.rustam.Movie_Website.exception.custom.IncorrectPasswordException;
import com.rustam.Movie_Website.mapper.UserMapper;
import com.rustam.Movie_Website.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UtilService utilService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RedisTemplate<String,String> redisTemplate;

    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        User user = User.builder()
                .name(userRegisterRequest.getName())
                .surname(userRegisterRequest.getSurname())
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .build();
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public AuthResponse login(AuthRequest authRequest) {
        User user = utilService.findByUsername(authRequest.getUsername());
        if (!passwordEncoder.matches(authRequest.getPassword(),user.getPassword())){
            throw new IncorrectPasswordException("password does not match");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(user.getId()));
        TokenPair tokenPair = utilService.tokenProvider(user.getId(), userDetails);
        String redisKey = "refresh_token:" + user.getId();
        redisTemplate.opsForValue().set(redisKey, tokenPair.getRefreshToken(), Duration.ofDays(2)); // 2 gün müddətinə saxla
        return AuthResponse.builder()
                .tokenPair(tokenPair)
                .build();
    }
}
