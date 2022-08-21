package com.example.service;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;

public interface LoginService {
    public LoginResponse login(LoginRequest loginRequest) throws Exception;
}
