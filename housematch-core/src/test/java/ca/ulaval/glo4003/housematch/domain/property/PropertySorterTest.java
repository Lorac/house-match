package ca.ulaval.glo4003.housematch.domain.property;

import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SortOrder;

import org.junit.Before;
import org.junit.Test;

public class PropertySorterTest {

    private static final Integer NUMBER_OF_TEST_ITEMS = 5;
    private static final Integer VIEW_COUNT_TEST_RANGE_UPPER_BOUND = 1000;

public class PropertySorterTest {

    private List<Property> propertyList;
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
            when(propertyMock.getViewCount()).thenReturn((int) (Math.random() * VIEW_COUNT_TEST_RANGE_UPPER_BOUND));
            properties.add(propertyMock);
        }
    }

    @Test
    public void sortingtByViewCountInAscendingOrderSortsThePropertiesByViewCountInAscendingOrder() {
        propertySorter.sortByViewCount(properties, SortOrder.ASCENDING);

        for (int i = 1; i < properties.size(); i++) {
            assertThat(properties.get(i - 1).getViewCount(), lessThanOrEqualTo(properties.get(i).getViewCount()));
        }
    }

    @Test
    public void sortingtByViewCountInDescendingOrderSortsThePropertiesByViewCountInAscendingOrder() {
        propertySorter.sortByViewCount(properties, SortOrder.DESCENDING);

        for (int i = 1; i < properties.size(); i++) {
            assertThat(properties.get(i - 1).getViewCount(), greaterThanOrEqualTo(properties.get(i).getViewCount()));
        }
    }
}      

}