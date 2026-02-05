package com.riquelmemr.financetrack.strategy.cookie;

import com.riquelmemr.financetrack.enums.Cookie;
import org.springframework.http.ResponseCookie;

public interface CookieStrategy<T> {

    ResponseCookie create(T data);

    Cookie getCookie();

}
