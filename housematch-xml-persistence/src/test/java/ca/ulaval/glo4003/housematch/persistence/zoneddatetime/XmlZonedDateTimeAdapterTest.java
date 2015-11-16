package ca.ulaval.glo4003.housematch.persistence.zoneddatetime;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

public class XmlZonedDateTimeAdapterTest {

    private static final ZonedDateTime SAMPLE_DATE = ZonedDateTime.now();
    private XmlZonedDateTimeAdapter xmlZonedDateTimeAdapter;
    private ZonedDateTime zonedDateTime;
    private XmlZonedDateTime xmlZonedDateTime;
    
    @Before
    public void init() {
        xmlZonedDateTimeAdapter = new XmlZonedDateTimeAdapter();
        zonedDateTime = SAMPLE_DATE;
        initXmlZonedDateTime();
    }
    
    private void initXmlZonedDateTime() {
        xmlZonedDateTime = new XmlZonedDateTime();
        xmlZonedDateTime.date = zonedDateTime.toString();
    }
    
    @Test
    public void zonedDateTimeAttributesAreConvertedDuringMarshalling() {
        xmlZonedDateTime = xmlZonedDateTimeAdapter.marshal(zonedDateTime);
        assertEquals(SAMPLE_DATE.toString(), xmlZonedDateTime.date);
    }

    @Test
    public void xmlZonedDateTimeAttributesAreConvertedDuringUnmarshalling() {
        zonedDateTime = xmlZonedDateTimeAdapter.unmarshal(xmlZonedDateTime);
        assertEquals(SAMPLE_DATE, zonedDateTime);
    }
}
