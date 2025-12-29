package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
    UserModel findByUsernameOrEmail(String username, String email);
}
