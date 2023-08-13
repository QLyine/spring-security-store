package com.example.user.service.services;

import com.example.user.service.domain.UserDTO;

public interface AuthService {
    String login(UserDTO userDTO);
}
