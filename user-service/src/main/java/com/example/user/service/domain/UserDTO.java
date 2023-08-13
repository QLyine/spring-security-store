package com.example.user.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class UserDTO {

    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(Long id, String username, String password, Long calories) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.calories = calories;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Long id;

    private String username;
    private String password;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Long calories;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCalories() {
        return calories;
    }

    public Long getId() {
        return id;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
