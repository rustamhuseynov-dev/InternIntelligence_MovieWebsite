package com.rustam.Movie_Website.service;

import com.rustam.Movie_Website.dao.entity.BaseUser;
import com.rustam.Movie_Website.dao.entity.User;
import com.rustam.Movie_Website.dao.enums.Role;
import com.rustam.Movie_Website.dao.repository.BaseUserRepository;
import com.rustam.Movie_Website.dto.TokenPair;
import com.rustam.Movie_Website.dto.request.*;
import com.rustam.Movie_Website.dto.response.*;
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
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final BaseUserRepository baseUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UtilService utilService;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        BaseUser baseUser = User.builder()
                .name(userRegisterRequest.getName())
                .surname(userRegisterRequest.getSurname())
                .phone(userRegisterRequest.getPhone())
                .email(userRegisterRequest.getEmail())
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .authorities(Collections.singleton(Role.USER))
                .enabled(true)
                .build();
        baseUserRepository.save(baseUser);
        return userMapper.toResponse(baseUser);
    }

    public AuthResponse login(AuthRequest authRequest) {
        BaseUser baseUser = utilService.findByUsername(authRequest.getUsername());
        if (!passwordEncoder.matches(authRequest.getPassword(), baseUser.getPassword())) {
            throw new IncorrectPasswordException("password does not match");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(baseUser.getId()));
        TokenPair tokenPair = utilService.tokenProvider(baseUser.getId(), userDetails);
        String redisKey = "refresh_token:" + baseUser.getId();
        redisTemplate.opsForValue().set(redisKey, tokenPair.getRefreshToken(), Duration.ofDays(2)); // 2 gün müddətinə saxla
        return AuthResponse.builder()
                .tokenPair(tokenPair)
                .build();
    }

    public UserDeletedResponse delete(UUID id) {
        BaseUser baseUser = utilService.findById(id);
        String currentUsername = utilService.getCurrentUsername();
        utilService.validation(baseUser.getId(), currentUsername);
        UserDeletedResponse deletedResponse = new UserDeletedResponse();
        modelMapper.map(baseUser, deletedResponse);
        deletedResponse.setText("This user was deleted by you.");
        baseUserRepository.delete(baseUser);
        return deletedResponse;
    }

    public UserRegisterResponse read(UUID id) {
        BaseUser baseUser = utilService.findById(id);
        return userMapper.toResponse(baseUser);
    }

    public List<UserRegisterResponse> readAll() {
        List<BaseUser> baseUsers = utilService.findAll();
        return userMapper.toResponses(baseUsers);
    }

    public UserUpdateResponse update(UserUpdateRequest userUpdateRequest) {
        String currentUsername = utilService.getCurrentUsername();
        BaseUser user = utilService.findById(userUpdateRequest.getId());
        utilService.validation(currentUsername, user.getId());
        boolean exists = utilService.findAllBy().stream()
                .map(User::getUsername)
                .anyMatch(existingUsername -> existingUsername.equals(userUpdateRequest.getUsername()));
        if (exists) {
            throw new ExistsException("This username is already taken.");
        }
        modelMapper.map(userUpdateRequest, user);
        baseUserRepository.save(user);
        return userMapper.toUpdated(user);
    }

    public String refreshToken(RefreshRequest request) {
        return utilService.refreshToken(request);
    }

    public AdminRegisterResponse forAdmin(AdminRegisterRequest adminRegisterRequest) {
        User user = (User) utilService.findByUsername(adminRegisterRequest.getUsername());
        user.setAuthorities(Collections.singleton(Role.ADMIN));
        user.setIban(utilService.generateIbanForUser());
        user.setStartDay(LocalDate.now());
        baseUserRepository.save(user);
        return userMapper.toAdminResponse(user);
    }

    public String selectToBeAdmin(SelectToBeRequest selectToBeRequest) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        BaseUser user = utilService.findByUsername(selectToBeRequest.getUsername());
        user.setAuthorities(Collections.singleton(Role.REQUEST_ADMIN));
        baseUserRepository.save(user);
        return "You can now apply to become an admin.";
    }
}
