package ca.ulaval.glo4003.housematch.domain.statistics;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;

public class PropertyStatisticsTest {

    private static final Integer SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR = 4;
    private static final Map<PropertyType, Integer> SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE = new HashMap<>();

    private PropertyStatistics propertyStatistics;

    @Before
    public void init() {
        propertyStatistics = new PropertyStatistics();
    }

    @Test
    public void settingTheNumberOfSoldPropertiesThisYearSetsTheNumberOfSoldPropertiesThisYear() {
        propertyStatistics.setNumberOfSoldPropertiesThisYear(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR);
        assertEquals(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR, propertyStatistics.getNumberOfSoldPropertiesThisYear());
    }

    @Test
    public void settingTheNumberOfPropertiesForSaleSetsTheNumberOfPropertiesForSale() {
        propertyStatistics.setNumberOfPropertiesForSale(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
        assertEquals(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE, propertyStatistics.getNumberOfPropertiesForSale());
    }
}
