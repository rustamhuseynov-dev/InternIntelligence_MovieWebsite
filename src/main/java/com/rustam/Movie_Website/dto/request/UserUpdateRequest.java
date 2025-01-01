package com.rustam.Movie_Website.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "The id column cannot be empty.")
    private UUID id;
    @NotBlank(message = "The name column cannot be empty.")
    private String name;
    @NotBlank(message = "The surname column cannot be empty.")
    private String surname;
    @Pattern(regexp = "\\(\\d{3}\\)-\\d{3}-\\d{4}", message = "The phone number must be in the format (000)-000-0000.")
    private String phone;
    @Pattern(regexp = "[a-z]+@[a-z]+\\.[a-z]{2,4}", message = "Enter your email address.")
    private String email;
    @NotBlank(message = "The username column cannot be empty.")
    private String username;
}
