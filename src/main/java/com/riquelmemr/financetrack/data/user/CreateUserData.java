package com.riquelmemr.financetrack.data.user;

import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.model.RoleModel;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateUserData {

    private final String name;
    private final String email;
    private final String password;
    private final String username;
    private final List<RoleModel> roles;

    public CreateUserData(RegisterUserRequest request, List<RoleModel> roles) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.username = request.getUsername();
        this.roles = roles;
    }
}
