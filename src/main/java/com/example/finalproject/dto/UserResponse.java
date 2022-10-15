package com.example.finalproject.dto;

import com.example.finalproject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Builder
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String token;
    private Role role;
}
