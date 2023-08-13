package com.example.user.service.controllers;

import com.example.user.service.domain.UserDTO;
import com.example.user.service.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserDTO userDTO){
        String token = authService.login(userDTO);

        return ResponseEntity.ok(token);
    }
}
