package ca.ulaval.glo4003.housematch.persistence.marshalling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlZonedDateTimeAdapter;

public class XmlZonedDateTimeAdapterTest {

    private static final ZonedDateTime SAMPLE_ZONED_DATE_TIME = ZonedDateTime.now();

    private XmlZonedDateTimeAdapter xmlZonedDateTimeAdapter;

    @Before
    public void init() {
        xmlZonedDateTimeAdapter = new XmlZonedDateTimeAdapter();
    }

    @Test
    public void zonedDateTimeIsMarshalledToAString() throws Exception {
        String marshalledZonedDateTime = xmlZonedDateTimeAdapter.marshal(SAMPLE_ZONED_DATE_TIME);
        assertFalse(marshalledZonedDateTime.isEmpty());
    }

    @Test
    public void zonedDateTimeStringIsUnmarshalledToAZonedDateTime() throws Exception {
        String marshalledZonedDateTime = xmlZonedDateTimeAdapter.marshal(SAMPLE_ZONED_DATE_TIME);
        ZonedDateTime unmarshalledZonedDateTime = xmlZonedDateTimeAdapter.unmarshal(marshalledZonedDateTime);
        assertEquals(SAMPLE_ZONED_DATE_TIME, unmarshalledZonedDateTime);
    }
}
