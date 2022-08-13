package com.example.service;

import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SignupServiceImpl implements SignupService {
    private final UserRepository userRepository;

    public SignupServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String signup(User user) {
        log.info("making sign up for a user with username={}, email={} and phone-number={}", user.getUserName(), user.getEmail(), user.getPhoneNumber());
        userRepository.save(user);
        return "signed up successfully";
    }
}
