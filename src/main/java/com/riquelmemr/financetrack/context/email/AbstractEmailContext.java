package com.riquelmemr.financetrack.context.email;

import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.isNull;

public abstract class AbstractEmailContext<T> implements EmailContext<T> {

    private static final String TEMPLATES_BASE_PATH = "email/templates/";

    protected String loadHtmlContent(String templateName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TEMPLATES_BASE_PATH + templateName);

        if (isNull(inputStream)) {
            throw new IllegalStateException("Email template file not found: " + templateName);
        }

        return new String(inputStream.readAllBytes());
    }

}
