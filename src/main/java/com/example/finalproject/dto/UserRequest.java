package com.example.finalproject.dto;

import com.example.finalproject.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserRequest {
    private String email;
    private String password;
    private Role role;
}