package com.example.service;

import com.example.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User getByUserName(String userName) throws UsernameNotFoundException;
}
