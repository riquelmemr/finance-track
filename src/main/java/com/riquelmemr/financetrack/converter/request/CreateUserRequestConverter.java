package com.riquelmemr.financetrack.converter.request;

import com.riquelmemr.financetrack.dto.request.CreateUserRequest;
import com.riquelmemr.financetrack.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserRequestConverter implements Converter<CreateUserRequest, UserModel> {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserModel convert(CreateUserRequest source) {
        UserModel target = new UserModel();

        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setUsername(source.getUsername());
        target.setPassword(passwordEncoder.encode(source.getPassword()));

        return target;
    }
}
