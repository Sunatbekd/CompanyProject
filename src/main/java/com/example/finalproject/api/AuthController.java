package com.example.finalproject.api;

import com.example.finalproject.dto.UserRequest;
import com.example.finalproject.dto.UserRequestt;
import com.example.finalproject.dto.UserResponse;
import com.example.finalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;


    @PostMapping("/login")
    public UserResponse login(@RequestBody UserRequestt authRequest) {
        return userService.login(authRequest);
    }


    @PostMapping("/registration")
    public UserResponse create(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }
}
