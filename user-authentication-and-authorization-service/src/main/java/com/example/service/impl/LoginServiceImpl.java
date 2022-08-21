package com.example.service.impl;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.service.LoginService;
import com.example.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public LoginServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        log.info("making log in for user with username={}", loginRequest.getUserName());
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userName,
                    password
            ));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        log.info("authenticating and generating JWT for user with username={}", loginRequest.getUserName());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        String jwt = jwtUtil.generateToken(userDetails);

        return new LoginResponse(jwt);
    }
}
