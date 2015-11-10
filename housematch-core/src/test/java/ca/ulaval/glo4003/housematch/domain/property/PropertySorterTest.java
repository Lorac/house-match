package ca.ulaval.glo4003.housematch.domain.property;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PropertySorterTest {

    private static final int HIGHEST = Integer.MAX_VALUE;
    private static final int MID = 0;
    private static final int LOWEST = Integer.MIN_VALUE;
    private PropertySorter propertySorter;
    private List<Property> properties;
    private Property mostViewProperty;
    private Property secondMostViewProperty;
    private Property thirdMostViewProperty;

    @Before
    public void init() {
        initMocks();
        propertySorter = new PropertySorter();
        properties = new ArrayList<>();

    }

    private void initMocks() {
        mostViewProperty = mock(Property.class);
        when(mostViewProperty.getViewCount()).thenReturn(HIGHEST);

        secondMostViewProperty = mock(Property.class);
        when(secondMostViewProperty.getViewCount()).thenReturn(MID);

        thirdMostViewProperty = mock(Property.class);
        when(thirdMostViewProperty.getViewCount()).thenReturn(LOWEST);
    }

    @Test
    public void test() {
        List<Property> expected = new ArrayList<>(3);
        expected.add(mostViewProperty);
        expected.add(secondMostViewProperty);
        expected.add(thirdMostViewProperty);

        properties.add(secondMostViewProperty);
        properties.add(thirdMostViewProperty);
        properties.add(mostViewProperty);

        propertySorter.sortByHighestViewCount(properties);

        assertEquals(expected, properties);
    }


}