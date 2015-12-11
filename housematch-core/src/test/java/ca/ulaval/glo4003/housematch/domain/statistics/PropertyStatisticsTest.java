package ca.ulaval.glo4003.housematch.domain.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

public class PropertyStatisticsTest {

    private static final Integer SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR = 4;
    private static final Integer SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE = 6;
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.CONDO_LOFT;

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
        propertyStatistics.setNumberOfPropertiesForSale(SAMPLE_PROPERTY_TYPE, SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
        Integer returnedNumberOfPropertiesForSale = propertyStatistics.getNumberOfPropertiesForSale().get(SAMPLE_PROPERTY_TYPE);
        assertEquals(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE, returnedNumberOfPropertiesForSale);
    }
}
