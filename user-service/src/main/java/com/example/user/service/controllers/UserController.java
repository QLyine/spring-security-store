package com.example.user.service.controllers;

import com.example.user.service.domain.UserDAO;
import com.example.user.service.domain.UserDTO;
import com.example.user.service.repository.UserRepository;
import com.example.user.service.services.JwtUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController()
public class UserController {

    public UserRepository userRepository;

    public JwtUserService userService;

    public UserController(UserRepository userRepository, JwtUserService jwtUserService) {
        this.userService = jwtUserService;
        this.userRepository = userRepository;
    }


    @GetMapping(path = "/user/username/{username}")
    public ResponseEntity<UserDTO> register(@PathVariable String username) throws Exception {
        UserDAO foundUser = userRepository.findByUsername(username);
        UserDTO userr = new UserDTO(foundUser.getId(), foundUser.getUsername(), foundUser.getPassword(), foundUser.getCalories());
        return ResponseEntity.ok(userr);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<Optional<UserDTO>> register(@PathVariable Long id) throws Exception {
        Optional<UserDTO> userDTO = userRepository.findById(id).map(userDao -> new UserDTO(userDao.getId(), userDao.getUsername(), userDao.getPassword(), userDao.getCalories()));
        return ResponseEntity.ofNullable(userDTO);
    }

    @GetMapping(path = "/user")
    public ResponseEntity<List<UserDTO>> getAll() {
        final List<UserDTO> userDTOS = new LinkedList<>();
        for (UserDAO userDAO : userRepository.findAll()) {
            final UserDTO userDTO = new UserDTO(userDAO.getId(), userDAO.getUsername(), userDAO.getPassword(), userDAO.getCalories());
            userDTOS.add(userDTO);
        }
        return ResponseEntity.ok(userDTOS);
    }

    @DeleteMapping(path = "/user/{id}")
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @PostMapping(path = "/user")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) throws Exception {
        UserDAO save = userService.save(userDTO);
        UserDTO returnDTO = new UserDTO(save.getUsername(), save.getPassword());
        return ResponseEntity.ok(returnDTO);
    }
}
