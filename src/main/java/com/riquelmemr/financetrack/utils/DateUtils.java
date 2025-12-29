package com.riquelmemr.financetrack.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter brazilDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private DateUtils() {}

    public static String toBrazilDateFormat(LocalDateTime localDateTime) {
        return localDateTime.format(brazilDateFormatter);
    }
}
