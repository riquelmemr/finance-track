package com.riquelmemr.financetrack.controller;

import com.riquelmemr.financetrack.dto.request.CreateUserRequest;
import com.riquelmemr.financetrack.dto.response.CreateUserResponse;
import com.riquelmemr.financetrack.dto.response.DataWrapperResponse;
import com.riquelmemr.financetrack.facade.user.UserFacade;
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
@RequestMapping(value = "/users")
public class UserController extends BaseController {

    private static final String USER_CREATED_SUCCESSFULLY_MESSAGE = "User created successfully";

    private final UserFacade userFacade;

    @PostMapping
    public ResponseEntity<DataWrapperResponse<CreateUserResponse>> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        CreateUserResponse response = userFacade.create(createUserRequest);
        return handleResponse(HttpStatus.CREATED, response, USER_CREATED_SUCCESSFULLY_MESSAGE);
    }
}
