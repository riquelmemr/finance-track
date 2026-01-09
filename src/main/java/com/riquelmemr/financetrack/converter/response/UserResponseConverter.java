package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.dto.response.UserResponse;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserResponseConverter implements Converter<UserModel, UserResponse> {

    @Override
    public UserResponse convert(UserModel source) {
        UserResponse target = new UserResponse();

        target.setId(source.getId());
        target.setEmail(source.getEmail());
        target.setName(source.getName());
        target.setUsername(source.getUsername());

        return target;
    }
}
