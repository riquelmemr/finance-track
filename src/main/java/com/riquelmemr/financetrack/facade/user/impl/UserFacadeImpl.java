package com.riquelmemr.financetrack.facade.user.impl;

import com.riquelmemr.financetrack.dto.response.UserResponse;
import com.riquelmemr.financetrack.facade.user.UserFacade;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.service.session.SessionService;
import com.riquelmemr.financetrack.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final SessionService sessionService;
    private final UserService userService;
    private final Converter<UserModel, UserResponse> userResponseConverter;

    @Override
    public UserResponse getMe() {
        UserModel requester = sessionService.getCurrentUser();
        UserModel user = userService.findByUsername(requester.getUsername());
        return userResponseConverter.convert(user);
    }
}
