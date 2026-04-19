package com.example.librarymanager.security;

import com.example.librarymanager.user.application.port.UserRepository;
import com.example.librarymanager.user.domain.User;
import com.example.librarymanager.user.domain.UserId;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // called with email (login) or id (JWT filter)
    @Override
    public UserDetails loadUserByUsername(String emailOrId) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(emailOrId)
                .or(() -> userRepository.findById(UserId.of(emailOrId)))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + emailOrId));

        return new org.springframework.security.core.userdetails.User(
                user.getId().value(),
                user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
