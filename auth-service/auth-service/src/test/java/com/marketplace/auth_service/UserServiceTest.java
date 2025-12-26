package com.marketplace.auth_service;

import com.marketplace.auth_service.domain.Role;
import com.marketplace.auth_service.domain.User;
import com.marketplace.auth_service.dto.RegisterRequest;
import com.marketplace.auth_service.repository.RoleRepository;
import com.marketplace.auth_service.repository.UserRepository;
import com.marketplace.auth_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Test
    void registerCreatesUser() {
        UserRepository ur = Mockito.mock(UserRepository.class);
        RoleRepository rr = Mockito.mock(RoleRepository.class);

        Mockito.when(ur.findByUsername("alice")).thenReturn(Optional.empty());
        Mockito.when(rr.findByName("ROLE_USER")).thenReturn(Optional.of(Role.builder().id("r1").name("ROLE_USER").build()));

        Mockito.when(ur.save(Mockito.any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserService us = new UserService(ur, rr);

        RegisterRequest req = new RegisterRequest();
        req.setUsername("alice");
        req.setPassword("pass");

        User u = us.register(req);
        assertEquals("alice", u.getUsername());
        assertNotNull(u.getPassword());
        assertFalse(u.getPassword().isEmpty());
    }
}
