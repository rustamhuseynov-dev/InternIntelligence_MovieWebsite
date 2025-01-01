package com.rustam.Movie_Website.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshRequest {
    @NotBlank(message = "The refreshToken column cannot be empty.")
    private String refreshToken;
}
