package com.riquelmemr.financetrack.service.cookie;

import com.riquelmemr.financetrack.enums.Cookie;
import org.springframework.http.ResponseCookie;

public interface CookieService {
    <T> ResponseCookie create(Cookie cookie, T data);
}
