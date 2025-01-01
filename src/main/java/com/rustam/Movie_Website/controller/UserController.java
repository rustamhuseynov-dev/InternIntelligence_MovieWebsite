package com.rustam.Movie_Website.controller;

import com.rustam.Movie_Website.dto.request.AdminRegisterRequest;
import com.rustam.Movie_Website.dto.request.AuthRequest;
import com.rustam.Movie_Website.dto.request.UserRegisterRequest;
import com.rustam.Movie_Website.dto.request.UserUpdateRequest;
import com.rustam.Movie_Website.dto.response.*;
import com.rustam.Movie_Website.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest userRegisterRequest){
        return new ResponseEntity<>(userService.register(userRegisterRequest), HttpStatus.CREATED);
    }

    @PutMapping(path = "/for-admin")
    public ResponseEntity<AdminRegisterResponse> register(@RequestBody AdminRegisterRequest adminRegisterRequest){
        return new ResponseEntity<>(userService.registerAdmin(adminRegisterRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(userService.login(authRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<UserUpdateResponse> update(@RequestBody UserUpdateRequest userUpdateRequest){
        return new ResponseEntity<>(userService.update(userUpdateRequest),HttpStatus.OK);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<UserRegisterResponse>> readAll(){
        return new ResponseEntity<>(userService.readAll(),HttpStatus.OK);
    }

    @GetMapping(path = "/read/{id}")
    public ResponseEntity<UserRegisterResponse> read(@PathVariable UUID id){
        return new ResponseEntity<>(userService.read(id),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<UserDeletedResponse> delete(@PathVariable UUID id){
        return new ResponseEntity<>(userService.delete(id),HttpStatus.ACCEPTED);
    }
}
