package com.riquelmemr.financetrack.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "tb_access_token", indexes = {
        @Index(name = "idx_access_token_authentication_id", columnList = "authentication_id"),
        @Index(name = "idx_access_token_token", columnList = "token"),
        @Index(name = "idx_access_token_user", columnList = "user_id")
})
@Getter
@Setter
public class AccessTokenModel extends ItemModel {

    @Column(unique = true)
    private String authenticationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private Boolean active;
}
