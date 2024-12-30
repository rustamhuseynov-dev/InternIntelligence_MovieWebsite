package com.rustam.Movie_Website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String username;
    private String password;

}
