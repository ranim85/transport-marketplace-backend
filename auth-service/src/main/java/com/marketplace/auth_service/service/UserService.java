package com.marketplace.auth_service.service;

import com.marketplace.auth_service.domain.Role;
import com.marketplace.auth_service.domain.User;
import com.marketplace.auth_service.dto.RegisterRequest;
import com.marketplace.auth_service.repository.RoleRepository;
import com.marketplace.auth_service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public User register(RegisterRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(
                Role.builder().name("ROLE_USER").build()
        ));

        User u = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .roles(Collections.singleton(userRole))
                .build();

        return userRepository.save(u);
    }
}
