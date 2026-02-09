package com.riquelmemr.financetrack.scheduler.impl;

import com.riquelmemr.financetrack.scheduler.Scheduler;
import com.riquelmemr.financetrack.service.accesstoken.AccessTokenService;
import com.riquelmemr.financetrack.service.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanUpInvalidTokensScheduler implements Scheduler {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Scheduled(cron = "0 0 3 * * *")
    public void perform() {
        log.info("Starting the task of removing invalid or expired tokens.");

        Instant threshold = Instant.now().minus(7, ChronoUnit.DAYS);

        accessTokenService.deleteExpiredAndRevoked(threshold);
        refreshTokenService.deleteExpiredAndRevoked(threshold);

        log.info("Removed of all invalid tokens completed.");
    }
}
