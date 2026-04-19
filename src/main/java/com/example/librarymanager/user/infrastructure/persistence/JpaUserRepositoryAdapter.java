package com.example.librarymanager.user.infrastructure.persistence;

import com.example.librarymanager.user.application.port.UserRepository;
import com.example.librarymanager.user.domain.User;
import com.example.librarymanager.user.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepositoryAdapter implements UserRepository {

    private final SpringJpaUserRepository repo;

    public JpaUserRepositoryAdapter(SpringJpaUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User save(User user) {
        return repo.save(JpaUser.fromDomain(user)).toDomain();
    }

    @Override
    public Optional<User> findById(UserId id) {
        return repo.findById(id.value()).map(JpaUser::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email).map(JpaUser::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }
}
