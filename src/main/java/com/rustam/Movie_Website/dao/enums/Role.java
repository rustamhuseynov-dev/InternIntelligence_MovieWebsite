package com.rustam.Movie_Website.dao.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    REQUEST_ADMIN("REQUEST_ADMIN"),
    USER("USER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
