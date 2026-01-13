package com.riquelmemr.financetrack.service.auth.impl;

import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.enums.Role;
import com.riquelmemr.financetrack.exception.ModelAlreadyExistsException;
import com.riquelmemr.financetrack.exception.ResourceNotAllowedException;
import com.riquelmemr.financetrack.model.RoleModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.UserRepository;
import com.riquelmemr.financetrack.security.userdetails.UserDetailsImpl;
import com.riquelmemr.financetrack.service.accesstoken.AccessTokenService;
import com.riquelmemr.financetrack.service.auth.AuthService;
import com.riquelmemr.financetrack.service.role.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenService accessTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username);
        return new UserDetailsImpl(user);
    }

    @Override
    @Transactional
    public UserModel register(RegisterUserRequest request, UserModel adminUser) {
        UserModel userAlreadyExists = userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail());

        if (nonNull(userAlreadyExists)) {
            throw new ModelAlreadyExistsException("User with username or e-mail already exists");
        }

        RoleModel role = roleService.findByName(request.getRole().name());

        if (isNull(adminUser) && isAdminRole(role)) {
            throw new ResourceNotAllowedException("You cannot create an admin user.");
        }

        UserModel user = UserModel.builder()
                .withName(request.getName())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withUsername(request.getUsername())
                .withEnabled(true)
                .withRoles(List.of(role))
                .build();

        return userRepository.save(user);
    }

    @Override
    public void logout(UserModel user) {
        accessTokenService.deleteToken(user);
    }

    private boolean isAdminRole(RoleModel role) {
        return Role.ADMIN.name().equals(role.getName());
    }
}
