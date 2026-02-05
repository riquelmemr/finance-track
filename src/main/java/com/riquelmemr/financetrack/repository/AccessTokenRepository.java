package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.model.AccessTokenModel;
import com.riquelmemr.financetrack.model.RefreshTokenModel;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessTokenModel, Long> {

    Optional<AccessTokenModel> findByToken(String token);

    Optional<AccessTokenModel> findByUser(UserModel user);

    @Modifying
    @Query("""
    UPDATE AccessTokenModel
    SET revoked = true
    WHERE refreshToken = :refreshTokenModel
    """)
    void revokeAllByRefreshToken(RefreshTokenModel refreshTokenModel);
}
