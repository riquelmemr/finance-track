package com.riquelmemr.financetrack.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_access_token", indexes = {
        @Index(name = "idx_access_token_token", columnList = "token"),
        @Index(name = "idx_access_token_user", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
public class AccessTokenModel extends TokenModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_token_id", nullable = false)
    private RefreshTokenModel refreshToken;
}
