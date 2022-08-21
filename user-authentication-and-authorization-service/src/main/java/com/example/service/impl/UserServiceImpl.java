package com.example.service.impl;

import com.example.dto.CustomUserDetails;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user with username={}", username);
        User user = getByUserName(username);
        System.out.println(user);
        return new CustomUserDetails(user);
    }

    @Override
    public List<User> getAll() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public User getByUserName(String userName) throws UsernameNotFoundException {
        log.info("Getting a user with username={}", userName);
        return userRepository.findByUsername(userName);
    }
}