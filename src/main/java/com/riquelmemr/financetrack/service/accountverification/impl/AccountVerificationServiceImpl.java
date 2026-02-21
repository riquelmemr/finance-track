package com.riquelmemr.financetrack.service.accountverification.impl;

import com.riquelmemr.financetrack.data.email.AccountVerificationEmailData;
import com.riquelmemr.financetrack.enums.EmailTemplate;
import com.riquelmemr.financetrack.exception.ModelNotFoundException;
import com.riquelmemr.financetrack.model.AccountVerificationModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.AccountVerificationRepository;
import com.riquelmemr.financetrack.service.accountverification.AccountVerificationService;
import com.riquelmemr.financetrack.service.email.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.riquelmemr.financetrack.utils.TokenGeneratorUtils.generateToken;

@Service
@RequiredArgsConstructor
public class AccountVerificationServiceImpl implements AccountVerificationService {

    private final AccountVerificationRepository accountVerificationRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public AccountVerificationModel create(UserModel user) {
        AccountVerificationModel accountVerification = new AccountVerificationModel();

        accountVerification.setUser(user);
        accountVerification.setToken(generateToken());
        accountVerification.setExpirationDate(getExpirationDate());

        sendVerificationEmail(user, accountVerification.getToken());

        return accountVerificationRepository.save(accountVerification);
    }

    @Override
    @Transactional
    public void delete(AccountVerificationModel accountVerification) {
        accountVerificationRepository.delete(accountVerification);
    }

    @Override
    public AccountVerificationModel findByToken(String token) {
        return accountVerificationRepository.findByToken(token)
                .orElseThrow(() -> new ModelNotFoundException("Account verification token not found."));
    }

    private LocalDateTime getExpirationDate() {
        return LocalDateTime.now().plusMinutes(5);
    }

    private void sendVerificationEmail(UserModel user, String token) {
        AccountVerificationEmailData emailData = new AccountVerificationEmailData(
                user.getName(), user.getEmail(), token);

        emailService.sendEmail(EmailTemplate.ACCOUNT_VERIFICATION, emailData);
    }
}
