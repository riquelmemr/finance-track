package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.model.AccessTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessTokenModel, Long> {
    Optional<AccessTokenModel> findByAuthenticationId(String authenticationId);
    Optional<AccessTokenModel> findByToken(String token);
}
