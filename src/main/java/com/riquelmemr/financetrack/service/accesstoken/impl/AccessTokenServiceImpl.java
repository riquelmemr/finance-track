package com.riquelmemr.financetrack.service.accesstoken.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.exception.AuthenticationException;
import com.riquelmemr.financetrack.exception.ModelNotFoundException;
import com.riquelmemr.financetrack.model.AccessTokenModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.AccessTokenRepository;
import com.riquelmemr.financetrack.security.generator.HashGenerator;
import com.riquelmemr.financetrack.service.accesstoken.AccessTokenService;
import com.riquelmemr.financetrack.service.jwt.JwtService;
import com.riquelmemr.financetrack.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;
    private final HashGenerator hashGenerator;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public AccessTokenModel generateToken(String username) {
        String authenticationId = hashGenerator.generate(username);

        Optional<AccessTokenModel> accessTokenModelOpt = accessTokenRepository.findByAuthenticationId(authenticationId);

        if (accessTokenModelOpt.isPresent()) {
            AccessTokenModel accessTokenModel = accessTokenModelOpt.get();

            try {
                jwtService.validateToken(accessTokenModel.getToken());
                return accessTokenModel;
            } catch (JWTVerificationException ex) {
                accessTokenRepository.delete(accessTokenModel);
                return createToken(authenticationId, accessTokenModel.getUser());
            }
        }

        UserModel userModel = userService.findByUsername(username);

        if (nonNull(userModel)) {
            return createToken(authenticationId, userModel);
        }

        throw new AuthenticationException("An occurred error when create new token.");
    }

    @Override
    public DecodedJWT validateToken(String token) {
        Optional<AccessTokenModel> accessTokenModelOpt = findByToken(token);

        if (accessTokenModelOpt.isPresent()) {
            AccessTokenModel accessTokenModel = accessTokenModelOpt.get();
            return jwtService.validateToken(accessTokenModel.getToken());
        }

        throw new AuthenticationException("Token not found.");
    }

    @Override
    public void deleteToken(UserModel user) {
        Optional<AccessTokenModel> accessTokenOpt = accessTokenRepository.findByUser(user);

        if (accessTokenOpt.isEmpty()) {
            throw new ModelNotFoundException("Token not found.");
        }

        accessTokenRepository.delete(accessTokenOpt.get());
    }

    private Optional<AccessTokenModel> findByToken(String token) {
        return accessTokenRepository.findByToken(token);
    }

    private AccessTokenModel createToken(String authenticationId, UserModel userModel) {
        DecodedJWT decodedAccessToken = generateAndDecodeToken(userModel.getUsername());

        AccessTokenModel accessTokenModel = new AccessTokenModel();

        accessTokenModel.setAuthenticationId(authenticationId);
        accessTokenModel.setUser(userModel);
        accessTokenModel.setToken(decodedAccessToken.getToken());
        accessTokenModel.setExpiresAt(decodedAccessToken.getExpiresAtAsInstant());
        accessTokenModel.setActive(true);

        return accessTokenRepository.save(accessTokenModel);
    }

    private DecodedJWT generateAndDecodeToken(String username) {
        String token = jwtService.generateToken(username);
        return jwtService.validateToken(token);
    }
}
