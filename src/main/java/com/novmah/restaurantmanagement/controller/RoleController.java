package com.novmah.restaurantmanagement.controller;

import com.novmah.restaurantmanagement.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/{name}")
    public String createRole(@PathVariable String name) {
        return roleService.createRole(name);
    }

    @PatchMapping("/{id}/name/{name}")
    public String updateRoleName(@PathVariable Integer id, @PathVariable String name) {
        return roleService.updateRoleName(id, name);
    }

    @DeleteMapping("/{id}")
    public String deleteRoleById(@PathVariable Integer id) {
        return roleService.deleteRoleById(id);
    }

    @GetMapping("/{id}")
    public String getRoleNameById(@PathVariable Integer id) {
        return roleService.getRoleNameById(id);
    }

    @GetMapping
    public List<String> getAllRoleName() {
        return roleService.getAllRoleName();
    }

}
