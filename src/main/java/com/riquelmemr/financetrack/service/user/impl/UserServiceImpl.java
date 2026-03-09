package com.riquelmemr.financetrack.service.user.impl;

import com.riquelmemr.financetrack.exception.AccountVerificationException;
import com.riquelmemr.financetrack.exception.ModelNotFoundException;
import com.riquelmemr.financetrack.model.AccountVerificationModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.UserRepository;
import com.riquelmemr.financetrack.security.userdetails.UserDetailsImpl;
import com.riquelmemr.financetrack.service.accountverification.AccountVerificationService;
import com.riquelmemr.financetrack.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountVerificationService accountVerificationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username);
        return new UserDetailsImpl(userModel);
    }

    @Override
    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void resendAccountVerification(String email) {
        UserModel user = userRepository.findByEmail(email);

        if (isNull(user)) {
            throw new ModelNotFoundException("User not found with e-mail " + email);
        }

        if (user.isVerified()) {
            throw new AccountVerificationException("Account with e-mail " + email + " is already verified");
        }

        AccountVerificationModel accountVerification = accountVerificationService.create(user);
        user.setAccountVerification(accountVerification);
        userRepository.save(user);
    }

    @Override
    public void verifyAccount(String token) {
        AccountVerificationModel accountVerification = accountVerificationService.findByToken(token);

        if (accountVerification.getExpirationDate().isBefore(LocalDateTime.now())) {
            accountVerificationService.delete(accountVerification);
            throw new AccountVerificationException("Account verification token has expired.");
        }

        UserModel user = accountVerification.getUser();
        user.setVerified(true);
        userRepository.save(user);
    }
}
