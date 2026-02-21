package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.model.AccountVerificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountVerificationRepository extends JpaRepository<AccountVerificationModel, Long> {

    Optional<AccountVerificationModel> findByToken(String token);

}
