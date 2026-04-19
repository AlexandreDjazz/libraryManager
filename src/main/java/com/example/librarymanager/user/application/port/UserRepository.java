package com.example.librarymanager.user.application.port;

import com.example.librarymanager.user.domain.User;
import com.example.librarymanager.user.domain.UserId;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
