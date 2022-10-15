package com.example.finalproject.dto;

import com.example.finalproject.entity.Role;
import com.example.finalproject.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Login {
    public LoginResponse toLoginView(String token, String message, User user) {
        var loginResponse = new LoginResponse();
        if (user != null) {
            getAuthority(loginResponse, user.getRole());
        }
        loginResponse.setMessage(message);
        loginResponse.setJwtToken(token);
        return loginResponse;
    }

    private void getAuthority(LoginResponse loginResponse, Role roles) {
        Set<String> authorities = new HashSet<>();
        authorities.add(roles.getAuthority());
        loginResponse.setAuthorities(authorities);
    }
}
