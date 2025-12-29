package com.riquelmemr.financetrack.config;

import com.riquelmemr.financetrack.enums.Role;
import com.riquelmemr.financetrack.model.RoleModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.RoleRepository;
import com.riquelmemr.financetrack.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Configuration
@RequiredArgsConstructor
public class InitializerConfig implements CommandLineRunner {

    @Value("${spring.datasource.init.admin.username}")
    private String username;

    @Value("${spring.datasource.init.admin.password}")
    private String password;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        synchronizeRoles();
        synchronizeUserAdministrator();
    }

    private void synchronizeRoles() {
        List<String> roles = new ArrayList<>();

        for (Role roleEnum : Role.values()) {
            Optional<RoleModel> roleModelOpt = roleRepository.findByName(roleEnum.name());

            if (roleModelOpt.isPresent()) {
                roles.add(roleModelOpt.get().getName());
            } else {
                RoleModel created = createRole(roleEnum);
                roles.add(created.getName());
            }
        }

        System.out.println("Synchronized roles: " + roles);
    }

    private RoleModel createRole(Role roleEnum) {
        RoleModel role = new RoleModel();
        role.setName(roleEnum.name());
        return roleRepository.save(role);
    }

    private void synchronizeUserAdministrator() {
        if (username.isBlank() || password.isBlank()) {
            log.info("Admin credentials is not provided. Skipping admin creation.");
            return;
        }

        Optional.ofNullable(userRepository.findByUsername(username))
                .ifPresentOrElse(
                        userModel -> log.info("Admin already exists. Skipping creation."),
                        this::createUser
                );
    }

    private void createUser() {
        RoleModel adminRole = getAdminRole();

        UserModel userModel = new UserModel();

        userModel.setEmail(username);
        userModel.setUsername(username);
        userModel.setPassword(passwordEncoder.encode(password));
        userModel.setName(username);
        userModel.setRoles(List.of(adminRole));
        userModel.setEnabled(true);

        userRepository.save(userModel);
        log.info("Administrator created successfully.");
    }

    private RoleModel getAdminRole() {
        return roleRepository.findByName(Role.ADMIN.name())
                .orElseThrow(() -> new IllegalStateException("ADMIN role not found"));
    }
}
