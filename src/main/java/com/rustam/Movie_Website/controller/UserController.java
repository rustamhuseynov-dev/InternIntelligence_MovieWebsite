package com.rustam.Movie_Website.controller;

import com.rustam.Movie_Website.dto.request.AuthRequest;
import com.rustam.Movie_Website.dto.request.UserRegisterRequest;
import com.rustam.Movie_Website.dto.response.AuthResponse;
import com.rustam.Movie_Website.dto.response.UserRegisterResponse;
import com.rustam.Movie_Website.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest userRegisterRequest){
        return new ResponseEntity<>(userService.register(userRegisterRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(userService.login(authRequest),HttpStatus.OK);
    }
}
