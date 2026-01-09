package com.riquelmemr.financetrack.controller;

import com.riquelmemr.financetrack.dto.response.DataWrapperResponse;
import com.riquelmemr.financetrack.dto.response.UserResponse;
import com.riquelmemr.financetrack.facade.user.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController extends BaseController {

    private final static String USER_FOUND_SUCCESSFULLY_MESSAGE = "User found with successfully.";

    private final UserFacade userFacade;

    @Secured({ "ROLE_BASIC", "ROLE_ADMIN" })
    @GetMapping("/me")
    public ResponseEntity<DataWrapperResponse<UserResponse>> getMe() {
        UserResponse response = userFacade.getMe();
        return handleResponse(HttpStatus.OK, response, USER_FOUND_SUCCESSFULLY_MESSAGE);
    }
}
