package ca.ulaval.glo4003.housematch.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class DateFormatter {
    private static final String SHORT_DATE_FORMAT = "yyyy/MM/dd";
    private static final String SHORT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

    private DateFormatter() {

    }

    public static String toShortDate(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ofPattern(SHORT_DATE_FORMAT));
    }

    public static String toShortDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ofPattern(SHORT_DATE_TIME_FORMAT));
    }
}
