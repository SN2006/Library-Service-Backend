package com.example.app.backend.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN ("ADMIN"),
    USER ("USER");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}
