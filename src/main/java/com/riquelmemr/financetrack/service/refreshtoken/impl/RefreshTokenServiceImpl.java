package com.riquelmemr.financetrack.service.refreshtoken.impl;

import com.riquelmemr.financetrack.data.RefreshTokenData;
import com.riquelmemr.financetrack.exception.AuthenticationException;
import com.riquelmemr.financetrack.exception.ModelNotFoundException;
import com.riquelmemr.financetrack.model.RefreshTokenModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.RefreshTokenRepository;
import com.riquelmemr.financetrack.security.generator.HashGenerator;
import com.riquelmemr.financetrack.service.jwt.JwtService;
import com.riquelmemr.financetrack.service.refreshtoken.RefreshTokenService;
import com.riquelmemr.financetrack.utils.TokenGeneratorUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final String REFRESH_TOKEN_INVALID_MESSAGE = "Refresh token invalid or expired.";

    private final JwtService jwtService;
    private final HashGenerator hashGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public RefreshTokenData generateToken(UserModel user) {
        String rawRefreshToken = TokenGeneratorUtils.generateToken();
        Instant refreshExpiresAt = jwtService.getRefreshTokenExpiration();

        RefreshTokenModel refreshToken = new RefreshTokenModel();
        refreshToken.setToken(hashGenerator.generate(rawRefreshToken));
        refreshToken.setRevoked(false);
        refreshToken.setExpiresAt(refreshExpiresAt);
        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);

        return RefreshTokenData.builder()
                .withToken(rawRefreshToken)
                .withExpiresAt(refreshExpiresAt)
                .withRefreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void revokeToken(RefreshTokenModel refreshToken) {
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public RefreshTokenModel validateToken(String refreshToken) {
        RefreshTokenModel refreshTokenModel = findByToken(refreshToken);

        if (refreshTokenModel.getRevoked()) {
            throw new AuthenticationException(REFRESH_TOKEN_INVALID_MESSAGE);
        }

        if (Instant.now().isAfter(refreshTokenModel.getExpiresAt())) {
            throw new AuthenticationException(REFRESH_TOKEN_INVALID_MESSAGE);
        }

        return refreshTokenModel;
    }

    @Override
    @Transactional
    public void deleteExpiredAndRevoked(Instant threshold) {
        refreshTokenRepository.deleteExpiredOrRevoked(threshold);
    }

    private RefreshTokenModel findByToken(String refreshToken) {
        String refreshTokenHash = hashGenerator.generate(refreshToken);

        return refreshTokenRepository.findByToken(refreshTokenHash)
                .orElseThrow(() -> new ModelNotFoundException("Refresh token not found."));
    }
}
