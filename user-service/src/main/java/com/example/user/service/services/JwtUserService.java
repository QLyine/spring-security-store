package com.example.user.service.services;

import com.example.user.service.domain.UserDAO;
import com.example.user.service.domain.UserDTO;
import com.example.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtUserService implements UserDetailsService {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        List<SimpleGrantedAuthority> authorities = user.getScopes().stream().map(SimpleGrantedAuthority::new).toList();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    public UserDAO save(UserDTO user) {
        UserDAO newUser = new UserDAO();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        Set<String> scopes = new HashSet<>();
        scopes.add("SCOPE_user");
        scopes.add("SCOPE_product");
        scopes.add("SCOPE_order");
        newUser.setScopes(scopes);
        return userDao.save(newUser);
    }
}
