package ca.ulaval.glo4003.housematch.domain.property;

import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

public class PropertySorterTest {

    private static final Integer NUMBER_OF_TEST_ITEMS = 5;
    private static final Integer SAMPLE_VIEW_COUNT_LOWER_BOUND = 0;
    private static final Integer SAMPLE_VIEW_COUNT_UPPER_BOUND = 1000;
    private static final Integer SAMPLE_DATE_LOWER_BOUND = 0;
    private static final Integer SAMPLE_DATE_UPPER_BOUND = 1000;
    private static final Integer SAMPLE_PRICE_LOWER_BOUND = 10;
    private static final Integer SAMPLE_PRICE_UPPER_BOUND = 100000;

    private PropertySorter propertySorter;
    private List<Property> properties = new ArrayList<>();

    @Before
    public void init() {
        initPropertyMockList();
        propertySorter = new PropertySorter();
    }

    private void initPropertyMockList() {
        for (int i = 0; i < NUMBER_OF_TEST_ITEMS; i++) {
            Property propertyMock = mock(Property.class);
            initPropertyStubs(propertyMock);
            properties.add(propertyMock);
        }
    }

    private void initPropertyStubs(Property propertyMock) {
        when(propertyMock.getViewCount()).thenReturn(RandomUtils.nextInt(SAMPLE_VIEW_COUNT_LOWER_BOUND, SAMPLE_VIEW_COUNT_UPPER_BOUND));
        when(propertyMock.getCreationDate())
                .thenReturn(ZonedDateTime.now().plusDays(RandomUtils.nextInt(SAMPLE_DATE_LOWER_BOUND, SAMPLE_DATE_UPPER_BOUND)));
        when(propertyMock.getSellingPrice())
                .thenReturn(new BigDecimal(RandomUtils.nextInt(SAMPLE_PRICE_LOWER_BOUND, SAMPLE_PRICE_UPPER_BOUND)));
    }

    @Test
    public void sortingtBySellingPriceInAscendingOrderSortsThePropertiesBySellingPriceInAscendingOrder() {
        propertySorter.sortBySellingPriceInAscendingOrder(properties);
        for (int i = 1; i < properties.size(); i++) {
            assertThat(properties.get(i - 1).getSellingPrice(), lessThanOrEqualTo(properties.get(i).getSellingPrice()));
        }
    }

    @Test
    public void sortingtBySellingPriceInDescendingOrderSortsThePropertiesBySellingPriceInAscendingOrder() {
        propertySorter.sortBySellingPriceInDescendingOrder(properties);
        for (int i = 1; i < properties.size(); i++) {
            assertThat(properties.get(i - 1).getSellingPrice(), greaterThanOrEqualTo(properties.get(i).getSellingPrice()));
        }
    }

    @Test
    public void sortingtByCreationDateInAscendingOrderSortsThePropertiesByCreationDateInAscendingOrder() {
        propertySorter.sortByCreationDateInAscendingOrder(properties);
        for (int i = 1; i < properties.size(); i++) {
            assertThat(properties.get(i - 1).getCreationDate(), lessThanOrEqualTo(properties.get(i).getCreationDate()));
        }
    }

    @Test
    public void sortingtByCreationDateInDescendingOrderSortsThePropertiesByCreationDateInAscendingOrder() {
        propertySorter.sortByCreationDateInDescendingOrder(properties);
        for (int i = 1; i < properties.size(); i++) {
            assertThat(properties.get(i - 1).getCreationDate(), greaterThanOrEqualTo(properties.get(i).getCreationDate()));
        }
    }

    @Test
    public void sortingtByViewCountInDescendingOrderSortsThePropertiesByViewCountInAscendingOrder() {
        propertySorter.sortByViewCountInDescendingOrder(properties);
        for (int i = 1; i < properties.size(); i++) {
            assertThat(properties.get(i - 1).getViewCount(), greaterThanOrEqualTo(properties.get(i).getViewCount()));
        }
    }
}