package com.novmah.restaurantmanagement.service.impl;

import com.novmah.restaurantmanagement.entity.Role;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.repository.RoleRepository;
import com.novmah.restaurantmanagement.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public String createRole(String name) {
        Role role = Role.builder()
                .name(name).build();
        roleRepository.save(role);
        return role.getName();
    }

    @Override
    public String updateRoleName(Integer id, String name) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role ID", id.toString()));
        role.setName(name);
        roleRepository.save(role);
        return role.getName();
    }

    @Override
    public String deleteRoleById(Integer id) {
        roleRepository.deleteById(id);
        return "delete successfully!!!";
    }

    @Override
    public String getRoleNameById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role ID", id.toString()));
        return role.getName();
    }

    @Override
    public List<String> getAllRoleName() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(Role::getName).toList();
    }
}
