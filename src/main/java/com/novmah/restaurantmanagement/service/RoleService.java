package com.novmah.restaurantmanagement.service;

import java.util.List;

public interface RoleService {

    String createRole(String name);
    String updateRoleName(Integer id, String name);
    String deleteRoleById(Integer id);

    String getRoleNameById(Integer id);
    List<String> getAllRoleName();

}
