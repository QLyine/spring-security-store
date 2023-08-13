package com.example.user.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    @Column
    @JsonIgnore
    private String password;
    @Column
    @JsonIgnore
    @Convert(converter = ScopesConverter.class)
    private Set<String> scopes;

    @Column
    @JsonIgnore
    private boolean deleted = false;

    @Column
    @JsonIgnore
    private Long calories = 0l;

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


    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public Long getCalories() {
        return calories;
    }
}
