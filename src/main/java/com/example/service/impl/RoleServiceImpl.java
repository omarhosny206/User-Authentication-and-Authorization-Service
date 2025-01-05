package com.example.service.impl;

import com.example.exception.ApiError;
import com.example.model.Role;
import com.example.repository.RoleRepository;
import com.example.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> ApiError.badRequest("Role not found with id=" + id));
    }

    @Override
    public Role getByName(String name) {
        Role role = roleRepository.findByName(name);

        if (role == null) {
            throw ApiError.badRequest("Role not found with name=" + name);
        }

        return role;
    }

    @Override
    public Role getByNameOrNull(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
