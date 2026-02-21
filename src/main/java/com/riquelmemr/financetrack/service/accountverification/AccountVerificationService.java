package com.riquelmemr.financetrack.service.accountverification;

import com.riquelmemr.financetrack.model.AccountVerificationModel;
import com.riquelmemr.financetrack.model.UserModel;

public interface AccountVerificationService {

    AccountVerificationModel create(UserModel user);

    AccountVerificationModel findByToken(String token);

    void delete(AccountVerificationModel accountVerification);

}
