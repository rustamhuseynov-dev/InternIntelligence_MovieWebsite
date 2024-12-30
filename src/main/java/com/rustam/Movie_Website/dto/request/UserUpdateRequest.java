package com.rustam.Movie_Website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

    private UUID id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String username;
}
