package com.example.util;

import com.example.dto.CustomUser;
import com.example.model.User;
import org.springframework.security.core.Authentication;

public class AuthenticationUser {
    private static Authentication authentication;

    public static User get(Authentication authentication) {
        return ((CustomUser) authentication.getPrincipal()).getUser();
    }
}
