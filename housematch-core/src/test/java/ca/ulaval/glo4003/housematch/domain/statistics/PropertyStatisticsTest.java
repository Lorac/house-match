package ca.ulaval.glo4003.housematch.domain.statistics;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

public class PropertyStatisticsTest {

    private static final Integer SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR = 4;
    private static final Integer SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE = 6;
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.CONDO_LOFT;

    private StatisticFactory statisticFactoryMock;
    private Statistic<Integer> numberOfSoldPropertiesThisYearStatMock;
    private Statistic<Integer> numberOfPropertiesForSaleStatMock;
    private Statistic<Integer> statMock;
    private PropertyStatistics propertyStatistics;

    @Before
    public void init() {
        initMocks();
        initStubs();
        initStats();
        propertyStatistics = new PropertyStatistics(statisticFactoryMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        statisticFactoryMock = mock(StatisticFactory.class);
        numberOfSoldPropertiesThisYearStatMock = mock(Statistic.class);
        numberOfPropertiesForSaleStatMock = mock(Statistic.class);
        statMock = mock(Statistic.class);
    }

    private void initStubs() {
        when(statisticFactoryMock.createStatistic(anyString(), anyInt())).thenReturn(statMock);
        when(statisticFactoryMock.createStatistic(eq(PropertyStatistics.NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME), anyInt()))
                .thenReturn(numberOfSoldPropertiesThisYearStatMock);
        when(statisticFactoryMock.createStatistic(eq(getPropertiesForSaleStatName(SAMPLE_PROPERTY_TYPE)), anyInt()))
                .thenReturn(numberOfPropertiesForSaleStatMock);
    }

    private void initStats() {
        when(numberOfSoldPropertiesThisYearStatMock.getValue()).thenReturn(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR);
        when(numberOfPropertiesForSaleStatMock.getValue()).thenReturn(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
    }

    @Test
    public void gettingTheNumberOfSoldPropertiesThisYearGetsTheNumberOfSoldPropertiesThisYear() {
        Integer returnedValue = propertyStatistics.getNumberOfSoldPropertiesThisYear();
        assertEquals(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR, returnedValue);
    }

    @Test
    public void settingTheNumberOfSoldPropertiesThisYearSetsTheNumberOfSoldPropertiesThisYear() {
        propertyStatistics.setNumberOfSoldPropertiesThisYear(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR);
        verify(numberOfSoldPropertiesThisYearStatMock).setValue(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR);
    }

    @Test
    public void gettingTheNumberOfPropertiesForSaleGetsTheNumberOfPropertiesForSale() {
        Integer returnedValue = propertyStatistics.getNumberOfPropertiesForSale(SAMPLE_PROPERTY_TYPE);
        assertEquals(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE, returnedValue);
    }

    @Test
    public void settingTheNumberOfPropertiesForSaleSetsTheNumberOfPropertiesForSale() {
        propertyStatistics.setNumberOfPropertiesForSale(SAMPLE_PROPERTY_TYPE, SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
        verify(numberOfPropertiesForSaleStatMock).setValue(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
    }

    @Test
    public void gettingTheNumberOfPropertiesForSaleReturnsTheNumberOfpropertiesForSale() {
        propertyStatistics.setNumberOfPropertiesForSale(SAMPLE_PROPERTY_TYPE, SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
        Map<PropertyType, Statistic<Integer>> returnedNumberOfPropertiesForSale = propertyStatistics.getNumberOfPropertiesForSale();
        assertEquals(numberOfPropertiesForSaleStatMock, returnedNumberOfPropertiesForSale.get(SAMPLE_PROPERTY_TYPE));
    }

    private String getPropertiesForSaleStatName(PropertyType propertyType) {
        return String.format("%s_%s", PropertyStatistics.NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME, propertyType.name());
    }
}
