package com.riquelmemr.financetrack.factory;

import com.riquelmemr.financetrack.enums.Cookie;
import com.riquelmemr.financetrack.strategy.CookieStrategy;

public interface CookieFactory {
    <T> CookieStrategy<T> get(Cookie cookie);
}
