package ca.ulaval.glo4003.housematch.domain.property;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PropertiesFilterTest {

    private static final List<Property> SAMPLE_PROPERTY_LIST = new ArrayList<Property>();

    PropertiesFilter propertiesFilter;
    private Property propertyMock;
    private Property  aSecondPropertyMock;
    private Property  aThirdPropertyMock;

    @Before
    public void init() {
        initMocks();
        propertiesFilter = new PropertiesFilter();
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        aSecondPropertyMock = mock(Property.class);
        aThirdPropertyMock = mock(Property.class);
    }

    @Before
    public void setUp() throws Exception {
        SAMPLE_PROPERTY_LIST.clear();
    }

    @Test
    public void filterPropertiesInChronologicalOrderSortPropertiesFromOlderToNewer() throws Exception {
        createMixedListOfProperties();

        propertiesFilter.orderByAscendingDates(SAMPLE_PROPERTY_LIST);
        assertSame(SAMPLE_PROPERTY_LIST.get(0), aThirdPropertyMock);
        assertSame(SAMPLE_PROPERTY_LIST.get(1), propertyMock);
        assertSame(SAMPLE_PROPERTY_LIST.get(2), aSecondPropertyMock);
    }

    @Test
    public void filterPropertiesInReverseChronologicalOrderSortPropertiesFromNewerToOlder() throws Exception {
        createMixedListOfProperties();

        propertiesFilter.orderByDescendingDates(SAMPLE_PROPERTY_LIST);
        assertSame(SAMPLE_PROPERTY_LIST.get(0), aSecondPropertyMock);
        assertSame(SAMPLE_PROPERTY_LIST.get(1), propertyMock);
        assertSame(SAMPLE_PROPERTY_LIST.get(2), aThirdPropertyMock);
    }

    private void createMixedListOfProperties() throws ParseException {
        SAMPLE_PROPERTY_LIST.add(propertyMock);
        SAMPLE_PROPERTY_LIST.add(aSecondPropertyMock);
        SAMPLE_PROPERTY_LIST.add(aThirdPropertyMock);


        ZonedDateTime date1 = ZonedDateTime.now();
        ZonedDateTime date2 = ZonedDateTime.now().plusDays(1);
        ZonedDateTime date3 = ZonedDateTime.now().plusDays(2);

        when(propertyMock.getDate()).thenReturn(date2);
        when(aSecondPropertyMock.getDate()).thenReturn(date3);
        when(aThirdPropertyMock.getDate()).thenReturn(date1);

    }

}
