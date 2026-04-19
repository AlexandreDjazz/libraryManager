package com.example.librarymanager.user.domain;

public class User {
    private final UserId id;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private UserCategory category;
    private AccountStatus accountStatus;
    private Role role;

    public User(UserId id, String email, String passwordHash, String firstName,
                String lastName, UserCategory category, AccountStatus accountStatus, Role role) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.category = category;
        this.accountStatus = accountStatus;
        this.role = role;
    }

    public boolean isBlocked() {
        return accountStatus == AccountStatus.BLOCKED || accountStatus == AccountStatus.SUSPENDED;
    }

    public void block() { this.accountStatus = AccountStatus.BLOCKED; }
    public void unblock() { this.accountStatus = AccountStatus.ACTIVE; }

    public UserId getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public UserCategory getCategory() { return category; }
    public AccountStatus getAccountStatus() { return accountStatus; }
    public Role getRole() { return role; }
}
