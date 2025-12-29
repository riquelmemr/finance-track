package com.riquelmemr.financetrack.facade.user.impl;

import com.riquelmemr.financetrack.dto.request.CreateUserRequest;
import com.riquelmemr.financetrack.dto.response.CreateUserResponse;
import com.riquelmemr.financetrack.facade.user.UserFacade;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final Converter<CreateUserRequest, UserModel> createUserRequestConverter;
    private final Converter<UserModel, CreateUserResponse> createUserResponseConverter;

    @Override
    public CreateUserResponse create(CreateUserRequest createUserRequest) {
        UserModel user = createUserRequestConverter.convert(createUserRequest);
        UserModel userCreated = userService.create(user);
        return createUserResponseConverter.convert(userCreated);
    }
}
