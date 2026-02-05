package com.riquelmemr.financetrack.service.auth.impl;

import com.riquelmemr.financetrack.data.AuthenticationData;
import com.riquelmemr.financetrack.dto.request.AuthRequest;
import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.enums.Role;
import com.riquelmemr.financetrack.exception.ModelAlreadyExistsException;
import com.riquelmemr.financetrack.exception.ResourceNotAllowedException;
import com.riquelmemr.financetrack.model.AccessTokenModel;
import com.riquelmemr.financetrack.model.RefreshTokenModel;
import com.riquelmemr.financetrack.model.RoleModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.UserRepository;
import com.riquelmemr.financetrack.security.userdetails.UserDetailsImpl;
import com.riquelmemr.financetrack.service.accesstoken.AccessTokenService;
import com.riquelmemr.financetrack.service.auth.AuthService;
import com.riquelmemr.financetrack.service.refreshtoken.RefreshTokenService;
import com.riquelmemr.financetrack.service.role.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

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

        UserModel user = buildUserModel(request, role);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserModel register(RegisterUserRequest request) {
        return register(request, null);
    }

    @Override
    public AuthenticationData authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return accessTokenService.generateToken(userDetails.getUsername());
    }

    @Override
    @Transactional
    public AuthenticationData refresh(String refreshToken) {
        RefreshTokenModel refreshTokenModel = refreshTokenService.validateToken(refreshToken);
        refreshTokenService.revokeToken(refreshTokenModel);
        accessTokenService.revokeAllByRefreshToken(refreshTokenModel);
        return accessTokenService.generateToken(refreshTokenModel.getUser().getUsername());
    }

    @Override
    @Transactional
    public void logout(String token) {
        AccessTokenModel accessTokenModel = accessTokenService.findByToken(token);
        accessTokenService.revokeToken(accessTokenModel);
        refreshTokenService.revokeToken(accessTokenModel.getRefreshToken());
    }


    private boolean isAdminRole(RoleModel role) {
        return Role.ADMIN.name().equals(role.getName());
    }

    private UserModel buildUserModel(RegisterUserRequest request, RoleModel role) {
        return UserModel.builder()
                .withEmail(request.getEmail())
                .withName(request.getName())
                .withUsername(request.getUsername())
                .withEnabled(true)
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withRoles(List.of(role))
                .build();
    }
}
