package com.riquelmemr.financetrack.service.user;

import com.riquelmemr.financetrack.model.UserModel;

public interface UserService {

    UserModel create(UserModel user);

    UserModel findByUsername(String username);
}
