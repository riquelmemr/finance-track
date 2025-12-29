package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.dto.response.CreateUserResponse;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateUserResponseConverter implements Converter<UserModel, CreateUserResponse> {

    @Override
    public CreateUserResponse convert(UserModel source) {
        CreateUserResponse target = new CreateUserResponse();

        target.setEmail(source.getEmail());
        target.setName(source.getName());
        target.setUsername(source.getUsername());

        return target;
    }
}
