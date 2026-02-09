package com.riquelmemr.financetrack.service.cookie.impl;

import com.riquelmemr.financetrack.enums.Cookie;
import com.riquelmemr.financetrack.factory.CookieFactory;
import com.riquelmemr.financetrack.service.cookie.CookieService;
import com.riquelmemr.financetrack.strategy.CookieStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private final CookieFactory cookieFactory;

    @Override
    public <T> ResponseCookie create(Cookie cookie, T data) {
        CookieStrategy<T> strategy = cookieFactory.get(cookie);
        return strategy.create(data);
    }
}
