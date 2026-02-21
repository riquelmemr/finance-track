package com.riquelmemr.financetrack.data.email;

import lombok.Getter;

@Getter
public class EmailData {
    private String to;

    public EmailData(String to) {
        this.to = to;
    }
}
