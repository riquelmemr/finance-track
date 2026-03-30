package com.riquelmemr.financetrack.service.auth.impl;

import com.riquelmemr.financetrack.data.AuthenticationData;
import com.riquelmemr.financetrack.data.user.CreateUserData;
import com.riquelmemr.financetrack.dto.request.AuthRequest;
import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.enums.Role;
import com.riquelmemr.financetrack.exception.AccountVerificationException;
import com.riquelmemr.financetrack.exception.BadCredentialsException;
import com.riquelmemr.financetrack.exception.ModelAlreadyExistsException;
import com.riquelmemr.financetrack.exception.ResourceNotAllowedException;
import com.riquelmemr.financetrack.model.*;
import com.riquelmemr.financetrack.repository.UserRepository;
import com.riquelmemr.financetrack.security.userdetails.UserDetailsImpl;
import com.riquelmemr.financetrack.service.accesstoken.AccessTokenService;
import com.riquelmemr.financetrack.service.accountverification.AccountVerificationService;
import com.riquelmemr.financetrack.service.auth.AuthService;
import com.riquelmemr.financetrack.service.refreshtoken.RefreshTokenService;
import com.riquelmemr.financetrack.service.role.RoleService;
import com.riquelmemr.financetrack.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final int MAX_AUTHENTICATION_TOKENS_PER_USER = 5;

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final AccessTokenService accessTokenService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final Converter<CreateUserData, UserModel> createUserConverter;
    private final UserService userService;
    private final AccountVerificationService accountVerificationService;

    @Override
    @Transactional
    public UserModel register(RegisterUserRequest request, UserModel adminUser) {
        UserModel userAlreadyExists = userRepository
                .findByUsernameOrEmail(request.getUsername(), request.getEmail());

        if (nonNull(userAlreadyExists)) {
            throw new ModelAlreadyExistsException("User with username or e-mail already exists");
        }

        RoleModel role = validateRole(request.getRole(), adminUser);

        CreateUserData createUserData = new CreateUserData(request, List.of(role));
        UserModel user = createUserConverter.convert(createUserData);

        if (isNull(user)) {
            throw new IllegalArgumentException("Failed to create user from request data.");
        }

        AccountVerificationModel accountVerification = accountVerificationService.create(user);
        user.setAccountVerification(accountVerification);

        UserModel userCreated = userRepository.save(user);
        log.info("User with username {} created successfully.", userCreated.getUsername());

        return userCreated;
    }

    @Override
    @Transactional
    public UserModel register(RegisterUserRequest request) {
        return register(request, null);
    }

    @Override
    public AuthenticationData authenticate(AuthRequest authRequest) {
        UserModel user = userService.findByUsername(authRequest.getUsername());

        if (isNull(user)) {
            throw new BadCredentialsException("Username or password is invalid.");
        }

        if (!user.isVerified()) {
            throw new AccountVerificationException("Account is not verified.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        verifyQuantityOfAuthenticationTokens(user);

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

    private void verifyQuantityOfAuthenticationTokens(UserModel user) {
        List<AccessTokenModel> activeTokens = accessTokenService.findAllActiveByUser(user);

        if (activeTokens.size() > MAX_AUTHENTICATION_TOKENS_PER_USER) {
            AccessTokenModel oldestToken = activeTokens.get(0);
            accessTokenService.revokeToken(oldestToken);
            refreshTokenService.revokeToken(oldestToken.getRefreshToken());
        }
    }

    private RoleModel validateRole(Role role, UserModel adminUser) {
        RoleModel roleModel = roleService.findByName(role.name());

        if (isNull(adminUser) && isAdminRole(roleModel)) {
            throw new ResourceNotAllowedException("You cannot create an admin user.");
        }

        return roleModel;
    }

    private boolean isAdminRole(RoleModel role) {
        return Role.ADMIN.name().equals(role.getName());
    }
}
