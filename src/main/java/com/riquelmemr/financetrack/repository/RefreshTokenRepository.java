package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.model.RefreshTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
    Optional<RefreshTokenModel> findByToken(String tokenHash);

    @Modifying
    @Query("""
    DELETE FROM RefreshTokenModel
    WHERE expiresAt < :threshold
       OR revoked = true
    """)
    void deleteExpiredOrRevoked(Instant threshold);
}
