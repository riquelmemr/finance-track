package com.riquelmemr.financetrack.context.email;

import com.riquelmemr.financetrack.enums.EmailTemplate;

public interface EmailContext<T> {

    String getHtmlContent(T data);

    String getTo(T data);

    String getSubject();

    EmailTemplate getEmailTemplate();

}
