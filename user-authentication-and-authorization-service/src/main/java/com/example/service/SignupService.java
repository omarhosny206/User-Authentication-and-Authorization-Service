package com.example.service;

import com.example.dto.UserDto;
import com.example.model.User;
import org.springframework.stereotype.Service;

@Service
public interface SignupService {
    public String signup(UserDto userDto);
}
