package com.riquelmemr.financetrack.service.accesstoken.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.data.AuthenticationData;
import com.riquelmemr.financetrack.data.GeneratedAccessToken;
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

import java.time.Instant;

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
    public void deleteExpiredAndRevoked(Instant threshold) {
        accessTokenRepository.deleteExpiredOrRevoked(threshold);
    }

    @Override
    public AccessTokenModel findByToken(String rawToken) {
        String hashToken = hashGenerator.generate(rawToken);

        return accessTokenRepository.findByToken(hashToken)
                .orElseThrow(() -> new ModelNotFoundException("Token not found."));
    }

    private AuthenticationData generateAuthenticationTokens(UserModel user) {
        GeneratedAccessToken generatedAccessToken = jwtService.generateToken(user.getUsername());
        RefreshTokenData generatedRefreshToken = refreshTokenService.generateToken(user);

        AccessTokenModel accessTokenModel = buildAccessTokenModel(
                user, generatedAccessToken, generatedRefreshToken.getRefreshToken());

        accessTokenRepository.save(accessTokenModel);

        return AuthenticationData.builder()
                .withAccessToken(TokenData.builder()
                        .withTokenValue(generatedAccessToken.getValue())
                        .withExpiresAt(generatedAccessToken.getExpiresAt())
                        .build())
                .withRefreshToken(TokenData.builder()
                        .withTokenValue(generatedRefreshToken.getToken())
                        .withExpiresAt(generatedRefreshToken.getRefreshToken().getExpiresAt())
                        .build())
                .build();
    }

    private AccessTokenModel buildAccessTokenModel(UserModel user, GeneratedAccessToken accessToken, RefreshTokenModel refreshTokenModel) {
        AccessTokenModel accessTokenModel = new AccessTokenModel();

        accessTokenModel.setUser(user);
        accessTokenModel.setToken(hashGenerator.generate(accessToken.getValue()));
        accessTokenModel.setRevoked(false);
        accessTokenModel.setExpiresAt(accessToken.getExpiresAt());
        accessTokenModel.setRefreshToken(refreshTokenModel);

        return accessTokenModel;
    }
}
