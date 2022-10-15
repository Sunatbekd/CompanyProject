package com.example.finalproject.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    STUDENT,INSTRUCTOR,ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
