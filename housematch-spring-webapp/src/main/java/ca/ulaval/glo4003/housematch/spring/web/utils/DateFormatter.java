package ca.ulaval.glo4003.housematch.spring.web.utils;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

public final class DateFormatter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    private DateFormatter() {

    }

    public static String parse(ZonedDateTime zonedDateTime) {
        return DATE_FORMAT.format(Date.from(zonedDateTime.toInstant()));
    }

    public static ZonedDateTime toZonedDatetime(String stringDate) {
        return ZonedDateTime.parse(stringDate);
    }
}
