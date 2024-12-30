package com.rustam.Movie_Website.service;

import com.rustam.Movie_Website.dao.entity.User;
import com.rustam.Movie_Website.dao.repository.UserRepository;
import com.rustam.Movie_Website.dto.TokenPair;
import com.rustam.Movie_Website.dto.request.AuthRequest;
import com.rustam.Movie_Website.dto.request.RefreshRequest;
import com.rustam.Movie_Website.dto.request.UserRegisterRequest;
import com.rustam.Movie_Website.dto.request.UserUpdateRequest;
import com.rustam.Movie_Website.dto.response.AuthResponse;
import com.rustam.Movie_Website.dto.response.UserDeletedResponse;
import com.rustam.Movie_Website.dto.response.UserRegisterResponse;
import com.rustam.Movie_Website.dto.response.UserUpdateResponse;
import com.rustam.Movie_Website.exception.custom.ExistsException;
import com.rustam.Movie_Website.exception.custom.IncorrectPasswordException;
import com.rustam.Movie_Website.mapper.UserMapper;
import com.rustam.Movie_Website.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UtilService utilService;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;
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

    public UserDeletedResponse delete(UUID id) {
        User user = utilService.findById(id);
        String currentUsername = utilService.getCurrentUsername();
        utilService.validation(user.getId(),currentUsername);
        UserDeletedResponse deletedResponse = new UserDeletedResponse();
        modelMapper.map(user,deletedResponse);
        deletedResponse.setText("This user was deleted by you.");
        userRepository.delete(user);
        return deletedResponse;
    }

    public UserRegisterResponse read(UUID id) {
        User user = utilService.findById(id);
        return userMapper.toResponse(user);
    }

    public List<UserRegisterResponse> readAll() {
        List<User> users = utilService.findAll();
        return userMapper.toResponses(users);
    }

    public UserUpdateResponse update(UserUpdateRequest userUpdateRequest) {
        String currentUsername = utilService.getCurrentUsername();
        User user = utilService.findById(userUpdateRequest.getId());
        utilService.validation(currentUsername,user.getId());
        boolean exists = utilService.findAll().stream()
                .map(User::getUsername)
                .anyMatch(existingUsername -> existingUsername.equals(userUpdateRequest.getUsername()));
        if (exists){
            throw new ExistsException("This username is already taken.");
        }
        modelMapper.map(userUpdateRequest,user);
        userRepository.save(user);
        return userMapper.toUpdated(user);
    }

    public String refreshToken(RefreshRequest request) {
        return utilService.refreshToken(request);
    }
}
