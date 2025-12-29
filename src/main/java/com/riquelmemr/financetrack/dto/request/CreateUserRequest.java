package com.riquelmemr.financetrack.dto.request;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String name;
    private String email;
    private String username;
    private String password;
}
