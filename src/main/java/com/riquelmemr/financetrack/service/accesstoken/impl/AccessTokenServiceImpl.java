package com.riquelmemr.financetrack.service.accesstoken.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.data.AuthenticationData;
import com.riquelmemr.financetrack.data.RefreshTokenData;
import com.riquelmemr.financetrack.data.TokenData;
import com.riquelmemr.financetrack.exception.AuthenticationException;
import com.riquelmemr.financetrack.exception.ModelNotFoundException;
import com.riquelmemr.financetrack.model.AccessTokenModel;
import com.riquelmemr.financetrack.model.RefreshTokenModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.AccessTokenRepository;
import com.riquelmemr.financetrack.security.generator.HashGenerator;
import com.riquelmemr.financetrack.service.accesstoken.AccessTokenService;
import com.riquelmemr.financetrack.service.jwt.JwtService;
import com.riquelmemr.financetrack.service.refreshtoken.RefreshTokenService;
import com.riquelmemr.financetrack.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;
    private final HashGenerator hashGenerator;
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public AuthenticationData generateToken(String username) {
        UserModel userModel = userService.findByUsername(username);

        if (isNull(userModel)) {
            throw new AuthenticationException("An occurred error when create new token.");
        }

        return generateAuthenticationTokens(userModel);
    }

    @Override
    public DecodedJWT validateToken(String token) {
        AccessTokenModel accessTokenModel = findByToken(token);

        if (accessTokenModel.getRevoked()) {
            throw new AuthenticationException("Token revoked.");
        }

        return jwtService.validateToken(token);
    }

    @Override
    @Transactional
    public void revokeToken(AccessTokenModel accessTokenModel) {
        accessTokenModel.setRevoked(true);
        accessTokenRepository.save(accessTokenModel);
    }

    @Override
    @Transactional
    public void revokeAllByRefreshToken(RefreshTokenModel refreshTokenModel) {
        accessTokenRepository.revokeAllByRefreshToken(refreshTokenModel);
    }

    @Override
    @Transactional
    public void deleteToken(UserModel user) {
        Optional<AccessTokenModel> accessTokenOpt = accessTokenRepository.findByUser(user);

        if (accessTokenOpt.isEmpty()) {
            throw new ModelNotFoundException("Token not found.");
        }

        accessTokenRepository.delete(accessTokenOpt.get());
    }

    @Override
    public AccessTokenModel findByToken(String rawToken) {
        String hashToken = hashGenerator.generate(rawToken);

        return accessTokenRepository.findByToken(hashToken)
                .orElseThrow(() -> new ModelNotFoundException("Token not found."));
    }

    private AuthenticationData generateAuthenticationTokens(UserModel user) {
        String accessToken = jwtService.generateToken(user.getUsername());
        DecodedJWT decodedToken = jwtService.decodeToken(accessToken);

        RefreshTokenData generatedRefreshToken = refreshTokenService.generateToken(user);

        AccessTokenModel accessTokenModel = new AccessTokenModel();
        accessTokenModel.setUser(user);
        accessTokenModel.setToken(hashGenerator.generate(accessToken));
        accessTokenModel.setRevoked(false);
        accessTokenModel.setExpiresAt(decodedToken.getExpiresAtAsInstant());
        accessTokenModel.setRefreshToken(generatedRefreshToken.getRefreshToken());

        accessTokenRepository.save(accessTokenModel);

        TokenData accessTokenData = TokenData.builder()
                .withTokenValue(accessToken)
                .withExpiresAt(accessTokenModel.getExpiresAt())
                .build();

        TokenData refreshTokenData = TokenData.builder()
                .withTokenValue(generatedRefreshToken.getToken())
                .withExpiresAt(generatedRefreshToken.getRefreshToken().getExpiresAt())
                .build();

        return AuthenticationData.builder()
                .withAccessToken(accessTokenData)
                .withRefreshToken(refreshTokenData)
                .build();
    }
}
