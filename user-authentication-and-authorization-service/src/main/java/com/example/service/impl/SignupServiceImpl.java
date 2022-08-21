package com.example.service.impl;

import com.example.dto.UserDto;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class SignupServiceImpl implements SignupService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String signup(UserDto userDto) {
        User user = new User();
        Set<Role> roles = new HashSet<>();

        log.info("making sign up for a user with username={}, email={} and phone-number={}", userDto.getUsername(), userDto.getEmail(), userDto.getPhoneNumber());

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userDto.getRoles().forEach(r -> {
            Role role =  roleRepository.findByName(r);
            if(role != null)
                roles.add(role);
        });

        user.setRoles(roles);
        BeanUtils.copyProperties(userDto, user);
        System.out.println("HELLO --> " + userDto);
        System.out.println("HELLO --> " + user);
        userRepository.save(user);
        return "signed up successfully";
    }
}
