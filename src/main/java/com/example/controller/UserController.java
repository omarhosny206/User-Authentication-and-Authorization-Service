package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.util.AuthenticationUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAll(Authentication authentication) {
        System.out.println("AUTHENTICATED_USER: " + AuthenticationUser.get(authentication));
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getById(@PathVariable Long id, Authentication authentication) {
        System.out.println("AUTHENTICATED_USER: " + AuthenticationUser.get(authentication));
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        User authenticatedUser = AuthenticationUser.get(authentication);
        System.out.println("AUTHENTICATED_USER: " + authenticatedUser);
        return ResponseEntity.ok().body(userService.getByEmail(authenticatedUser.getEmail()));
    }
}
