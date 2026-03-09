package com.riquelmemr.financetrack.data.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountVerificationEmailData extends EmailData {

    private String name;
    private String baseUrl;
    private String verificationToken;

    public AccountVerificationEmailData(String name, String to, String verificationToken) {
        super(to);
        this.name = name;
        this.baseUrl = "http://localhost:5173";
        this.verificationToken = verificationToken;
    }
}
