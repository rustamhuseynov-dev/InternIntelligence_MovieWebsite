package com.rustam.Movie_Website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDeletedResponse {

    private UUID id;
    private String name;
    private String surname;
    private String username;
    private String phone;
    private String text;
}
