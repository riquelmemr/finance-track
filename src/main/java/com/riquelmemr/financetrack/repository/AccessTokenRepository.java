package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.model.AccessTokenModel;
import com.riquelmemr.financetrack.model.RefreshTokenModel;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessTokenModel, Long> {

    Optional<AccessTokenModel> findByToken(String token);

    @Modifying
    @Query("""
    UPDATE AccessTokenModel a
    SET a.revoked = true
    WHERE a.refreshToken = :refreshTokenModel
    """)
    void revokeAllByRefreshToken(RefreshTokenModel refreshTokenModel);

    @Modifying
    @Query("""
    DELETE FROM AccessTokenModel a
    WHERE a.expiresAt < :threshold
        OR (a.revoked = true)
    """)
    void deleteExpiredOrRevoked(Instant threshold);

    @Query("""
    SELECT a FROM AccessTokenModel a
    WHERE a.user = :user
        AND a.revoked = false
        AND a.expiresAt > CURRENT_TIMESTAMP
    """)
    List<AccessTokenModel> findAllActiveByUser(UserModel user);
}
