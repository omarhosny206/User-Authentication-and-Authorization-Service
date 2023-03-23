package com.example.service.impl;

import com.example.dto.SignupRequest;
import com.example.exception.ApiError;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.SignupService;
import com.example.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SignupServiceImpl implements SignupService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public SignupServiceImpl(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public User signup(SignupRequest signupRequest) {
        User savedUser = userService.getByEmailOrNull(signupRequest.getEmail());
        Role savedRole = roleService.getByName(signupRequest.getRole());

        if (savedUser != null) {
            throw ApiError.badRequest("This email already exists, choose another one");
        }

        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(savedRole);

        User storedUser = userService.save(user);
        return storedUser;
    }
}
