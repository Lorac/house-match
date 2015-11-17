package ca.ulaval.glo4003.housematch.domain.property;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PropertySorterTest {

    private List<Property> propertyList;
    private PropertySorter propertySorter;
    private Property propertyMock;
    private Property aSecondPropertyMock;
    private Property aThirdPropertyMock;

    @Before
    public void init() throws Exception {
        initMocks();
        propertySorter = new PropertySorter();
        propertyList = new ArrayList<Property>();
        initPropertiesList();
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        aSecondPropertyMock = mock(Property.class);
        aThirdPropertyMock = mock(Property.class);
    }

    private void initPropertiesList() throws ParseException {
        propertyList.add(propertyMock);
        propertyList.add(aSecondPropertyMock);
        propertyList.add(aThirdPropertyMock);

        ZonedDateTime date1 = ZonedDateTime.now();
        ZonedDateTime date2 = ZonedDateTime.now().plusDays(1);
        ZonedDateTime date3 = ZonedDateTime.now().plusDays(2);
        BigDecimal price1 = new BigDecimal("50000");
        BigDecimal price2 = new BigDecimal("100");
        BigDecimal price3 = new BigDecimal("150000");

        when(propertyMock.getCreationDate()).thenReturn(date2);
        when(propertyMock.getSellingPrice()).thenReturn(price2);
        when(aSecondPropertyMock.getCreationDate()).thenReturn(date3);
        when(aSecondPropertyMock.getSellingPrice()).thenReturn(price3);
        when(aThirdPropertyMock.getCreationDate()).thenReturn(date1);
        when(aThirdPropertyMock.getSellingPrice()).thenReturn(price1);
    }

    @Test
    public void sortingPropertiesByDateInAscendingOrderSortsThePropertiesFromOldestToNewest() {
        propertySorter.sortByDateInAscendingOrder(propertyList);
        assertTrue(propertyList.get(0).getCreationDate().isBefore(propertyList.get(1).getCreationDate()));
        assertTrue(propertyList.get(1).getCreationDate().isBefore(propertyList.get(2).getCreationDate()));
    }

    @Test
    public void sortingPropertiesByDateInDescendingOrderSortsThePropertiesFromNewestToOldest() {
        propertySorter.sortByDateInDescendingOrder(propertyList);
        assertTrue(propertyList.get(0).getCreationDate().isAfter(propertyList.get(1).getCreationDate()));
        assertTrue(propertyList.get(1).getCreationDate().isAfter(propertyList.get(2).getCreationDate()));
    }

    @Test
    public void filterPropertiesInAscendingOrderByPriceSortPropertiesFromCheapestToMostExpensive() throws Exception {
        propertySorter.sortByPriceInAscendingOrder(propertyList);
        assertSame(propertyList.get(0), propertyMock);
        assertSame(propertyList.get(1), aThirdPropertyMock);
        assertSame(propertyList.get(2), aSecondPropertyMock);
    }

    @Test
    public void filterPropertiesInDescendingOrderByPriceSortPropertiesFromMostExpensiveToCheapest() throws Exception {
        propertySorter.sortByPriceInDescendingOrder(propertyList);
        assertSame(propertyList.get(0), aSecondPropertyMock);
        assertSame(propertyList.get(1), aThirdPropertyMock);
        assertSame(propertyList.get(2), propertyMock);
    }

}
