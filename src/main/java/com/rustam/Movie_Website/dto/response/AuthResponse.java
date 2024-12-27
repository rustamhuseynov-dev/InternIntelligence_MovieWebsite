package com.rustam.Movie_Website.dto.response;

import com.rustam.Movie_Website.dto.TokenPair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private TokenPair tokenPair;
}
