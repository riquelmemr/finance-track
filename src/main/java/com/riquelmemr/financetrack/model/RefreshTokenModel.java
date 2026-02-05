package com.riquelmemr.financetrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_refresh_token")
@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenModel extends TokenModel {

    @OneToMany(mappedBy = "refreshToken")
    private List<AccessTokenModel> accessTokens = new ArrayList<>();

}
