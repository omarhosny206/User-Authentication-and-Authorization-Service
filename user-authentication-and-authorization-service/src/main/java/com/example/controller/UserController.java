package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/super-admin")
    public ResponseEntity<List<User>> getAllAsSuperAdmin() {
        log.info("Only user with role SUPER_ADMIN can consume this endpoint");
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllAsAdmin() {
        log.info("Only users with role [ADMIN, SUPER_ADMIN] can consume this endpoint");
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/manager")
    public ResponseEntity<List<User>> getAllAsManager() {
        log.info("Only users with role [MANAGER, ADMIN, SUPER_ADMIN] can consume this endpoint");
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllAsUser() {
        log.info("Only users with role [USER, MANAGER, ADMIN, SUPER_ADMIN] can consume this endpoint");
        return ResponseEntity.ok().body(userService.getAll());
    }
}
