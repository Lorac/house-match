package ca.ulaval.glo4003.housematch.spring.web.util;

import java.time.ZonedDateTime;

public class DateFormatter {

    public String toString(ZonedDateTime zonedDateTime) {
        return zonedDateTime.getYear()
                + "/"
                + zonedDateTime.getMonth().ordinal()
                + "/"
                + zonedDateTime.getDayOfMonth();
    }

    public ZonedDateTime toZonedDatetime(String stringDate) {
        return ZonedDateTime.parse(stringDate);
    }
}
