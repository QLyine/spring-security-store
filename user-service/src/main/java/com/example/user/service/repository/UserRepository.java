package com.example.user.service.repository;

import com.example.user.service.domain.UserDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Long> {
    UserDAO findByUsername(String username);
    boolean existsByUsername(String username);
}
