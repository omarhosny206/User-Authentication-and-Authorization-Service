package com.example.service;

import com.example.model.User;
import org.springframework.stereotype.Service;

@Service
public interface SignupService {
    public String signup(User user);
}
