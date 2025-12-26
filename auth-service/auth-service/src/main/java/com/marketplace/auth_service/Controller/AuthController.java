package com.marketplace.auth_service.Controller;

import com.marketplace.auth_service.domain.User;
import com.marketplace.auth_service.dto.AuthRequest;
import com.marketplace.auth_service.dto.AuthResponse;
import com.marketplace.auth_service.dto.RegisterRequest;
import com.marketplace.auth_service.repository.UserRepository;
import com.marketplace.auth_service.service.JwtService;
import com.marketplace.auth_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserService userService, UserRepository userRepository, JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User u = userService.register(req);
        return ResponseEntity.ok(u.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        return userRepository.findByUsername(req.getUsername())
                .map(user -> {
                    if (passwordEncoder.matches(req.getPassword(), user.getPassword())) {
                        String token = jwtService.generateToken(user.getId(), user.getUsername());
                        return ResponseEntity.ok(new AuthResponse(token));
                    } else {
                        return ResponseEntity.status(401).body("Invalid credentials");
                    }
                }).orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
