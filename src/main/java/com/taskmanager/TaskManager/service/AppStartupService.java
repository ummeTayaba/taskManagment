package com.taskmanager.TaskManager.service;

import com.taskmanager.TaskManager.models.AppUser;
import com.taskmanager.TaskManager.models.AppUserRole;
import com.taskmanager.TaskManager.repositories.AppUserRoleRepository;
import com.taskmanager.TaskManager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppStartupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppUserRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void insertDummyUsers() {
        AppUser appUser1 = userRepository.getAppUserByEmail("testUser1@gmail.com");
        Optional<AppUserRole> adminRole = roleRepository.findAppUserRoleByRoleName("ROLE_ADMIN");

        AppUser appUser2 = userRepository.getAppUserByEmail("testUser2@gmail.com");
        Optional<AppUserRole> userRole = roleRepository.findAppUserRoleByRoleName("ROLE_USER");

        if (appUser1 == null) {
            AppUser user = new AppUser();
            user.setEmail("testUser1@gmail.com");
            user.setPassword(encoder.encode("adminpassword123"));

            AppUserRole userAdminRole = adminRole.orElseGet(() -> {
                AppUserRole role = new AppUserRole();
                role.setRoleName("ROLE_ADMIN");

                return roleRepository.save(role);
            });

            Set<AppUserRole> roleSet = new HashSet<>();
            roleSet.add(userAdminRole);

            user.setRoles(roleSet);

            userRepository.save(user);
        }

        if (appUser2 == null) {
            AppUser user = new AppUser();
            user.setEmail("testUser2@gmail.com");
            user.setPassword(encoder.encode("password123"));

            AppUserRole userDefaultRole = userRole.orElseGet(() -> {
                AppUserRole role = new AppUserRole();
                role.setRoleName("ROLE_USER");

                return roleRepository.save(role);
            });

            Set<AppUserRole> roleSet = new HashSet<>();
            roleSet.add(userDefaultRole);

            user.setRoles(roleSet);

            userRepository.save(user);
        }
    }
}
