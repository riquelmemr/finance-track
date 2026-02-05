package com.riquelmemr.financetrack.factory.impl;

import com.riquelmemr.financetrack.enums.Cookie;
import com.riquelmemr.financetrack.exception.StrategyNotFoundException;
import com.riquelmemr.financetrack.factory.CookieFactory;
import com.riquelmemr.financetrack.strategy.cookie.CookieStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CookieFactoryImpl implements CookieFactory {

    private final List<CookieStrategy<?>> strategies;

    @Override
    @SuppressWarnings("unchecked")
    public <T> CookieStrategy<T> get(Cookie cookie) {
        return (CookieStrategy<T>) strategies.stream()
                .filter(s -> s.getCookie().equals(cookie))
                .findFirst()
                .orElseThrow(() ->
                        new StrategyNotFoundException("Strategy not found for cookie " + cookie.getKey() + ".")
                );
    }
}
