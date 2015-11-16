package ca.ulaval.glo4003.housematch.persistence.zoneddatetime;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlZonedDateTimeAdapter extends XmlAdapter<XmlZonedDateTime, ZonedDateTime> {

    @Override
    public ZonedDateTime unmarshal(XmlZonedDateTime xmlZonedDateTime) {
        return ZonedDateTime.parse(xmlZonedDateTime.date);
    }

    @Override
    public XmlZonedDateTime marshal(ZonedDateTime zonedDateTime) {
        XmlZonedDateTime xmlZonedDateTime = new XmlZonedDateTime();
        xmlZonedDateTime.date = zonedDateTime.toString();
        return xmlZonedDateTime;
    }
}
