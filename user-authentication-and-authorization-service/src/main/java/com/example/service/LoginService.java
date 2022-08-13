package com.example.service;

import com.example.model.LoginRequest;
import com.example.model.LoginResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginService {
    public LoginResponse login(LoginRequest loginRequest) throws Exception;
}
