package com.example.librarymanager.user.application.usecase;

import com.example.librarymanager.shared.DomainIdGenerator;
import com.example.librarymanager.shared.exception.ConflictException;
import com.example.librarymanager.user.application.port.UserRepository;
import com.example.librarymanager.user.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUser {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DomainIdGenerator idGenerator;

    public RegisterUser(UserRepository userRepository, PasswordEncoder passwordEncoder,
                        DomainIdGenerator idGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.idGenerator = idGenerator;
    }

    @Transactional
    public UserId handle(String email, String rawPassword, String firstName,
                         String lastName, UserCategory category) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Email already registered: " + email);
        }
        User user = new User(UserId.of(idGenerator.generate()), email,
                passwordEncoder.encode(rawPassword), firstName, lastName,
                category, AccountStatus.ACTIVE, Role.READER);
        return userRepository.save(user).getId();
    }
}