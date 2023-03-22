package com.example.service;

import com.example.model.Role;
import com.example.model.User;

import java.util.List;

public interface RoleService {
    public List<Role> getAll();
    public Role getById(Long id);
    public Role getByName(String name);
    public Role getByNameOrNull(String name);
    public Role save(Role role);
}
