package com.example.librarymanager.user.infrastructure.persistence;

import com.example.librarymanager.user.domain.*;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
class JpaUser {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private UserCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    protected JpaUser() {}

    static JpaUser fromDomain(User user) {
        JpaUser e = new JpaUser();
        e.id = user.getId().value();
        e.email = user.getEmail();
        e.passwordHash = user.getPasswordHash();
        e.firstName = user.getFirstName();
        e.lastName = user.getLastName();
        e.category = user.getCategory();
        e.accountStatus = user.getAccountStatus();
        e.role = user.getRole();
        return e;
    }

    User toDomain() {
        return new User(UserId.of(id), email, passwordHash, firstName,
                lastName, category, accountStatus, role);
    }
}
