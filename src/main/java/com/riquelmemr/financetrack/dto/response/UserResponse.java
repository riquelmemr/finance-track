package com.riquelmemr.financetrack.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
    private List<String> roles;
}
