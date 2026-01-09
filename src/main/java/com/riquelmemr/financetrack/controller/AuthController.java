package com.riquelmemr.financetrack.controller;

import com.riquelmemr.financetrack.dto.request.AuthRequest;
import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.dto.response.AuthResponse;
import com.riquelmemr.financetrack.dto.response.DataWrapperResponse;
import com.riquelmemr.financetrack.dto.response.UserResponse;
import com.riquelmemr.financetrack.facade.auth.AuthFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private static final String USER_LOGGED_SUCCESSFULLY_MESSAGE = "User logged with successfully.";
    private static final String USER_CREATED_SUCCESSFULLY_MESSAGE = "User created with successfully";

    private final AuthFacade authFacade;

    @PostMapping("/token")
    public ResponseEntity<DataWrapperResponse<AuthResponse>> authenticate(@RequestBody AuthRequest authRequest) {
        AuthResponse response = authFacade.authenticate(authRequest);
        return handleResponse(HttpStatus.OK, response, USER_LOGGED_SUCCESSFULLY_MESSAGE);
    }

    @PostMapping("/register")
    public ResponseEntity<DataWrapperResponse<UserResponse>> register(@RequestBody @Valid RegisterUserRequest createUserRequest) {
        UserResponse response = authFacade.register(createUserRequest);
        return handleResponse(HttpStatus.CREATED, response, USER_CREATED_SUCCESSFULLY_MESSAGE);
    }
}
