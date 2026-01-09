package com.riquelmemr.financetrack.dto.request;

import com.riquelmemr.financetrack.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class RegisterUserRequest {

    @Min(value = 3)
    private String name;

    @Email
    private String email;

    @Min(value = 3)
    private String username;

    @Min(value = 6)
    private String password;
    private Role role;
}
