package com.riquelmemr.financetrack.enums;

import lombok.Getter;

@Getter
public enum Cookie {

    REFRESH_TOKEN("refresh_token");

    private final String key;

    Cookie(String key) {
        this.key = key;
    }
}
