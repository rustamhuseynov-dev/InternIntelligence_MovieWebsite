package com.rustam.Movie_Website.dto.response;

import com.rustam.Movie_Website.dao.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRegisterResponse {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private Role role;
}
