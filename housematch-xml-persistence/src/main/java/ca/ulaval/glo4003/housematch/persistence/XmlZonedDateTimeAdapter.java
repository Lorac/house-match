package ca.ulaval.glo4003.housematch.persistence;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

    @Override
    public String marshal(ZonedDateTime zonedDateTime) throws Exception {
        return zonedDateTime.toString();
    }

    @Override
    public ZonedDateTime unmarshal(String stringDateTime) throws Exception {
        return ZonedDateTime.parse(stringDateTime);
    }

}
