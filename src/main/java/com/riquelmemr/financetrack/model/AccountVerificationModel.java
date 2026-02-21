package com.riquelmemr.financetrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_account_verification")
@Getter
@Setter
@NoArgsConstructor
public class AccountVerificationModel extends ItemModel {

    @OneToOne(mappedBy = "accountVerification")
    private UserModel user;

    private String token;

    private LocalDateTime expirationDate;
}
