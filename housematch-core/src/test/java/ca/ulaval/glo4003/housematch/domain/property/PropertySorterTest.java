package ca.ulaval.glo4003.housematch.domain.property;

import org.junit.Before;
import org.junit.Test;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PropertySorterTest {

    private List<Property> samplePropertyList;
    private PropertySorter propertySorter;
    private Property propertyMock;
    private Property  aSecondPropertyMock;
    private Property  aThirdPropertyMock;

    @Before
    public void init() {
        propertySorter = new PropertySorter();
        propertyMock = mock(Property.class);
        aSecondPropertyMock = mock(Property.class);
        aThirdPropertyMock = mock(Property.class);
        samplePropertyList = new ArrayList<Property>();
    }

    @Test
    public void sortingPropertiesByDateInAscendingOrderSortsThePropertiesFromOldestToNewest() throws Exception {
        initPropertiesList();

        propertySorter.sortByDateInAscendingOrder(samplePropertyList);
        assertTrue(samplePropertyList.get(0).getCreationDate().isBefore(samplePropertyList.get(1).getCreationDate()));
        assertTrue(samplePropertyList.get(1).getCreationDate().isBefore(samplePropertyList.get(2).getCreationDate()));
    }

    @Test
    public void sortingPropertiesByDateInDescendingOrderSortsThePropertiesFromNewestToOldest() throws Exception {
        initPropertiesList();

        propertySorter.sortByDateInDescendingOrder(samplePropertyList);

        assertTrue(samplePropertyList.get(0).getCreationDate().isAfter(samplePropertyList.get(1).getCreationDate()));
        assertTrue(samplePropertyList.get(1).getCreationDate().isAfter(samplePropertyList.get(2).getCreationDate()));
    }

    private void initPropertiesList() throws ParseException {
        samplePropertyList.add(propertyMock);
        samplePropertyList.add(aSecondPropertyMock);
        samplePropertyList.add(aThirdPropertyMock);

        ZonedDateTime date1 = ZonedDateTime.now();
        ZonedDateTime date2 = ZonedDateTime.now().plusDays(1);
        ZonedDateTime date3 = ZonedDateTime.now().plusDays(2);

        when(propertyMock.getCreationDate()).thenReturn(date2);
        when(aSecondPropertyMock.getCreationDate()).thenReturn(date3);
        when(aThirdPropertyMock.getCreationDate()).thenReturn(date1);
    }

}
