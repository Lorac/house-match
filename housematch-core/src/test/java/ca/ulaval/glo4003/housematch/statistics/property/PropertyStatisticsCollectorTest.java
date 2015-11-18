package ca.ulaval.glo4003.housematch.statistics.property;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.statistics.Statistic;
import ca.ulaval.glo4003.housematch.statistics.StatisticFactory;

public class PropertyStatisticsCollectorTest {

    private static final Integer SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR = 5;
    private static final Integer SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE = 2;
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.CONDO_LOFT;

    private StatisticFactory statisticFactoryMock;
    private Statistic<Integer> numberOfSoldPropertiesThisYearStatMock;
    private Statistic<Map<PropertyType, Integer>> numberOfPropertiesForSaleStatMock;
    private Map<PropertyType, Integer> numberOfPropertiesForSale = new HashMap<>();
    private Property propertyMock;

    private PropertyStatisticsCollector propertyStatisticsCollector;

    @Before
    public void init() {
        initMocks();
        stubMethods();
        initStats();
        propertyStatisticsCollector = new PropertyStatisticsCollector(statisticFactoryMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        statisticFactoryMock = mock(StatisticFactory.class);
        numberOfSoldPropertiesThisYearStatMock = mock(Statistic.class);
        numberOfPropertiesForSaleStatMock = mock(Statistic.class);
        propertyMock = mock(Property.class);
    }

    private void stubMethods() {
        when(statisticFactoryMock.createStatistic(eq(PropertyStatisticsCollector.NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME), anyInt()))
                .thenReturn(numberOfSoldPropertiesThisYearStatMock);
        when(statisticFactoryMock.createStatistic(eq(PropertyStatisticsCollector.NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME),
                Matchers.<Map<PropertyType, Integer>> any())).thenReturn(numberOfPropertiesForSaleStatMock);
        when(propertyMock.getPropertyType()).thenReturn(SAMPLE_PROPERTY_TYPE);
    }

    private void initStats() {
        when(numberOfSoldPropertiesThisYearStatMock.getValue()).thenReturn(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR);
        when(numberOfPropertiesForSaleStatMock.getValue()).thenReturn(numberOfPropertiesForSale);
        numberOfPropertiesForSale.put(SAMPLE_PROPERTY_TYPE, SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
    }

    @Test
    public void applyingPropertySaleIncrementsTheNumberOfSoldPropertiesThisYear() {
        propertyStatisticsCollector.applyPropertySale(propertyMock);
        verify(numberOfSoldPropertiesThisYearStatMock).setValue(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR + 1);
    }

    @Test
    public void applyingPropertySaleDecrementsTheNumberOfPropertiesForSaleInTheCorrespondingCategory() {
        propertyStatisticsCollector.applyPropertySale(propertyMock);
        assertEquals(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE - 1, (int) numberOfPropertiesForSale.get(propertyMock.getPropertyType()));
    }

    @Test
    public void applyingNewPropertyForSaleIncrementsTheNumberOfPropertiesForSaleInTheCorrespondingCategory() {
        propertyStatisticsCollector.applyNewPropertyForSale(propertyMock);
        assertEquals(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE + 1, (int) numberOfPropertiesForSale.get(propertyMock.getPropertyType()));
    }

    @Test
    public void gettingTheStatisticsCorrectlyCreatesTheStatisticsDTO() {
        PropertyStatistics propertyStatistics = propertyStatisticsCollector.getStatistics();

        assertEquals(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR, propertyStatistics.getNumberOfSoldPropertiesThisYear());
        assertEquals(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE,
                propertyStatistics.getNumberOfPropertiesForSale().get(propertyMock.getPropertyType()));
    }
}