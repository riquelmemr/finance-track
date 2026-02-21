package com.riquelmemr.financetrack.converter.data;

import com.riquelmemr.financetrack.data.user.CreateUserData;
import com.riquelmemr.financetrack.model.UserModel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserModelConverter implements Converter<CreateUserData, UserModel> {

    private final PasswordEncoder passwordEncoder;

    @Override
    @NonNull
    public UserModel convert(CreateUserData source) {
        UserModel target = new UserModel();

        target.setEmail(source.getEmail());
        target.setUsername(source.getUsername());
        target.setName(source.getName());
        target.setPassword(passwordEncoder.encode(source.getPassword()));
        target.setRoles(source.getRoles());
        target.setVerified(false);
        target.setEnabled(true);

        return target;
    }
}
