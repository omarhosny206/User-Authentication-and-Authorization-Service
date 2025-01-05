package com.example.service;

import com.example.dto.UserDto;
import com.example.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    public List<User> getAll();
    public User getById(Long id);
    public User getByUsername(String username);
    public User getByUsernameOrNull(String username);
    public User getByEmail(String email);
    public User getByEmailOrNull(String email);
    public User save(User user);
}
