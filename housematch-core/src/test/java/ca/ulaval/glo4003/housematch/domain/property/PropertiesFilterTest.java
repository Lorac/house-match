package ca.ulaval.glo4003.housematch.domain.property;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void filterPropertiesInChronologicalOrderSortPropertiesFromOlderToNewer() throws Exception {
        createMixedListOfProperties();

        propertiesFilter.orderByAscendingDates(SAMPLE_PROPERTY_LIST);
        assertEquals(SAMPLE_PROPERTY_LIST.get(0), aThirdPropertyMock);
        assertEquals(SAMPLE_PROPERTY_LIST.get(1), propertyMock);
        assertEquals(SAMPLE_PROPERTY_LIST.get(2), aSecondPropertyMock);
    }

    @Test
    public void filterPropertiesInReverseChronologicalOrderSortPropertiesFromNewerToOlder() throws Exception {
        createMixedListOfProperties();

        propertiesFilter.orderByDescendingDates(SAMPLE_PROPERTY_LIST);
        assertEquals(SAMPLE_PROPERTY_LIST.get(0), aSecondPropertyMock);
        assertEquals(SAMPLE_PROPERTY_LIST.get(1), propertyMock);
        assertEquals(SAMPLE_PROPERTY_LIST.get(2), aThirdPropertyMock);
    }

    private void createMixedListOfProperties() throws ParseException {
        SAMPLE_PROPERTY_LIST.add(propertyMock);
        SAMPLE_PROPERTY_LIST.add(aSecondPropertyMock);
        SAMPLE_PROPERTY_LIST.add(aThirdPropertyMock);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2009-12-31");
        Date date2 = sdf.parse("2010-01-31");
        Date date3 = sdf.parse("2013-01-31");

        when(propertyMock.getDate()).thenReturn(date2);
        when(aSecondPropertyMock.getDate()).thenReturn(date3);
        when(aThirdPropertyMock.getDate()).thenReturn(date1);

    }

}
