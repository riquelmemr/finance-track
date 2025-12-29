package com.riquelmemr.financetrack.service.user.impl;

import com.riquelmemr.financetrack.enums.Role;
import com.riquelmemr.financetrack.exception.ModelAlreadyExistsException;
import com.riquelmemr.financetrack.model.RoleModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.UserRepository;
import com.riquelmemr.financetrack.service.role.RoleService;
import com.riquelmemr.financetrack.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    @Transactional
    public UserModel create(UserModel user) {
        UserModel userAlreadyExists = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (nonNull(userAlreadyExists)) {
            throw new ModelAlreadyExistsException("User with username or e-mail already exists");
        }

        RoleModel basicRole = roleService.findByName(Role.BASIC.name());
        user.setRoles(List.of(basicRole));
        return userRepository.save(user);
    }

    @Override
    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
