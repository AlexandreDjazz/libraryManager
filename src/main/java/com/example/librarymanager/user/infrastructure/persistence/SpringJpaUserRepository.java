package com.example.librarymanager.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SpringJpaUserRepository extends JpaRepository<JpaUser, String> {
    Optional<JpaUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
