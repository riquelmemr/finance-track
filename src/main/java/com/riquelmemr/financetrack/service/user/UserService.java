package com.riquelmemr.financetrack.service.user;

import com.riquelmemr.financetrack.model.UserModel;

public interface UserService {
    UserModel findByUsername(String username);
}
