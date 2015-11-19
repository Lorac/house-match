package ca.ulaval.glo4003.housematch.domain.property;

import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.SortOrder;

public class PropertySorterTest {

    private static final Integer NUMBER_OF_TEST_ITEMS = 5;
    private static final Integer SAMPLE_INT_LOWER_BOUND = 0;
    private static final Integer SAMPLE_INT_UPPER_BOUND = 1000;
    private static final PropertySortColumn SAMPLE_PROPERTY_SORT_COLUMN = PropertySortColumn.VIEW_COUNT;

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
        when(propertyMock.getViewCount()).thenReturn(RandomUtils.nextInt(SAMPLE_INT_LOWER_BOUND, SAMPLE_INT_UPPER_BOUND));
    }

    @Test
    public void sortingtPropertiesInAscendingOrderSortsThePropertiesInAscendingOrder() {
        propertySorter.sort(properties, SAMPLE_PROPERTY_SORT_COLUMN, SortOrder.ASCENDING);
        for (int i = 1; i < properties.size(); i++) {
            assertThat(properties.get(i - 1).getViewCount(), lessThanOrEqualTo(properties.get(i).getViewCount()));
        }
    }

    @Test
    public void sortingtPropertiesInDescendingOrderSortsThePropertiesInDescendingOrder() {
        propertySorter.sort(properties, SAMPLE_PROPERTY_SORT_COLUMN, SortOrder.DESCENDING);
        for (int i = 1; i < properties.size(); i++) {
            assertThat(properties.get(i - 1).getViewCount(), greaterThanOrEqualTo(properties.get(i).getViewCount()));
        }
    }

    @Test
    public void sortingtPropertiesWithNoSortColumnSpecifiedDoesNotSortTheProperties() {
        List<Property> initialPropertyList = new ArrayList<>(properties);
        List<Property> returnedPropertyList = propertySorter.sort(properties, PropertySortColumn.NONE, SortOrder.ASCENDING);
        assertEquals(initialPropertyList, returnedPropertyList);
    }

    @Test
    public void sortingtPropertiesWithNoOrderSpecifiedDoesNotSortTheProperties() {
        List<Property> initialPropertyList = new ArrayList<>(properties);
        List<Property> returnedPropertyList = propertySorter.sort(properties, SAMPLE_PROPERTY_SORT_COLUMN, SortOrder.NONE);
        assertEquals(initialPropertyList, returnedPropertyList);
    }
}