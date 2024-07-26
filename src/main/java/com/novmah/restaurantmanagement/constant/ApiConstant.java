package com.novmah.restaurantmanagement.constant;

import com.novmah.restaurantmanagement.entity.Role;
import com.novmah.restaurantmanagement.entity.User;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.repository.RoleRepository;
import com.novmah.restaurantmanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ApiConstant implements CommandLineRunner {

    public static final Integer ROLE_ADMIN = 501;
    public static final Integer ROLE_MANAGER = 502;
    public static final Integer ROLE_CHEF = 503;
    public static final Integer ROLE_WAITER = 504;
    public static final Integer ROLE_CASHIER = 505;
    public static final Integer ROLE_USER = 506;

    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "10";
    public static final String SORT_BY = "id";
    public static final String SORT_DIR = "asc";

    private final RoleRepository roleRepository;

    public ApiConstant(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Role admin = new Role();
            admin.setId(ROLE_ADMIN);
            admin.setName("ROLE_ADMIN");

            Role manager = new Role();
            manager.setId(ROLE_MANAGER);
            manager.setName("ROLE_MANAGER");

            Role chef = new Role();
            chef.setId(ROLE_CHEF);
            chef.setName("ROLE_CHEF");

            Role waiter = new Role();
            waiter.setId(ROLE_WAITER);
            waiter.setName("ROLE_WAITER");

            Role cashier = new Role();
            cashier.setId(ROLE_CASHIER);
            cashier.setName("ROLE_CASHIER");

            Role customer = new Role();
            customer.setId(ROLE_USER);
            customer.setName("ROLE_USER");

            List<Role> roles = List.of(admin, manager, chef, waiter, cashier, customer);
            List<Role> result = roleRepository.saveAll(roles);
            result.forEach(r-> log.info(r.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
