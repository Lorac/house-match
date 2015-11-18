package ca.ulaval.glo4003.housematch.domain.property;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PropertySorterTest {

    private List<Property> propertyList;
    private PropertySorter propertySorter;
    private Property propertyMock;
    private Property aSecondPropertyMock;
    private Property aThirdPropertyMock;
    private static final ZonedDateTime SAMPLE_DATE_TIME_1 = ZonedDateTime.now();
    private static final ZonedDateTime SAMPLE_DATE_TIME_2 = ZonedDateTime.now().plusDays(1);
    private static final ZonedDateTime SAMPLE_DATE_TIME_3 = ZonedDateTime.now().plusDays(2);
    private static final BigDecimal SAMPLE_PRICE_1 = BigDecimal.valueOf(50000);
    private static final BigDecimal SAMPLE_PRICE_2 = BigDecimal.valueOf(100);
    private static final BigDecimal SAMPLE_PRICE_3 = BigDecimal.valueOf(150000);

    @Before
    public void init() throws Exception {
        initMocks();
        propertyList = new ArrayList<Property>();
        initPropertiesList();
        stubMethods();
        propertySorter = new PropertySorter();
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
    }

    private void stubMethods() {
        when(propertyMock.getCreationDate()).thenReturn(SAMPLE_DATE_TIME_2);
        when(propertyMock.getSellingPrice()).thenReturn(SAMPLE_PRICE_2);
        when(aSecondPropertyMock.getCreationDate()).thenReturn(SAMPLE_DATE_TIME_3);
        when(aSecondPropertyMock.getSellingPrice()).thenReturn(SAMPLE_PRICE_3);
        when(aThirdPropertyMock.getCreationDate()).thenReturn(SAMPLE_DATE_TIME_1);
        when(aThirdPropertyMock.getSellingPrice()).thenReturn(SAMPLE_PRICE_1);
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
        assertTrue(propertyList.get(0).getSellingPrice().compareTo(propertyList.get(1).getSellingPrice()) == -1);
        assertTrue(propertyList.get(1).getSellingPrice().compareTo(propertyList.get(2).getSellingPrice()) == -1);
    }

    @Test
    public void filterPropertiesInDescendingOrderByPriceSortPropertiesFromMostExpensiveToCheapest() throws Exception {
        propertySorter.sortByPriceInDescendingOrder(propertyList);
        assertTrue(propertyList.get(0).getSellingPrice().compareTo(propertyList.get(1).getSellingPrice()) == 1);
        assertTrue(propertyList.get(1).getSellingPrice().compareTo(propertyList.get(2).getSellingPrice()) == 1);
    }

}
