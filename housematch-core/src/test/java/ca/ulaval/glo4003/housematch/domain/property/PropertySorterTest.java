package ca.ulaval.glo4003.housematch.domain.property;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PropertySorterTest {

    private static final int TWO_VIEW = 2;
    private static final int ONE_VIEW = 1;
    private static final int NO_VIEWS = 0;

    private PropertySorter propertySorter;
    private List<Property> properties;
    private Property mostViewedProperty;
    private Property secondMostViewedProperty;
    private Property thirdMostViewedProperty;

    @Before
    public void init() {
        initMocks();
        initStubs();
        propertySorter = new PropertySorter();
        properties = new ArrayList<>();
    }

    private void initStubs() {
        when(mostViewedProperty.getViewCount()).thenReturn(TWO_VIEW);
        when(secondMostViewedProperty.getViewCount()).thenReturn(ONE_VIEW);
        when(thirdMostViewedProperty.getViewCount()).thenReturn(NO_VIEWS);
    }

    private void initMocks() {
        mostViewedProperty = mock(Property.class);
        secondMostViewedProperty = mock(Property.class);
        thirdMostViewedProperty = mock(Property.class);
    }

    @Test
    public void whenSortingByMostViewedItShouldSortByMostViewed() {
        List<Property> expected = new ArrayList<>(3);
        expected.add(mostViewedProperty);
        expected.add(secondMostViewedProperty);
        expected.add(thirdMostViewedProperty);

        properties.add(secondMostViewedProperty);
        properties.add(thirdMostViewedProperty);
        properties.add(mostViewedProperty);

        propertySorter.sortByHighestViewCount(properties);

        assertEquals(expected, properties);
    }
}