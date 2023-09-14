package com.mediscreen.gateway.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private final String role = "USER";

    @Override
    public String getAuthority() {
        return role;
    }
}
