package ca.ulaval.glo4003.housematch.statistics.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.statistics.StatisticsRepository;

public class PropertyStatisticsCollectorTest {

    private static final Integer SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR = 5;
    private static final Integer SAMPLE_NUMBER_OF_CONDOS_FOR_SALE = 2;

    private Map<PropertyType, Integer> numberOfPropertiesForSale = new HashMap<>();
    private StatisticsRepository statisticsRepositoryMock;
    private Property propertyMock;

    private PropertyStatisticsCollector propertyStatisticsCollector;

    @Before
    public void init() {
        initMocks();
        stubMethods();
        propertyStatisticsCollector = new PropertyStatisticsCollector(statisticsRepositoryMock);
        numberOfPropertiesForSale.put(PropertyType.CONDO_LOFT, SAMPLE_NUMBER_OF_CONDOS_FOR_SALE);
    }

    private void initMocks() {
        statisticsRepositoryMock = mock(StatisticsRepository.class);
        propertyMock = mock(Property.class);
    }

    private void stubMethods() {
        when(statisticsRepositoryMock.get(eq(PropertyStatisticsCollector.NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME), anyInt()))
                .thenReturn(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR);
        when(statisticsRepositoryMock.get(eq(PropertyStatisticsCollector.NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME), any(HashMap.class)))
                .thenReturn(numberOfPropertiesForSale);
        when(propertyMock.getPropertyType()).thenReturn(PropertyType.CONDO_LOFT);
    }

    @Test
    public void applyingPropertySaleIncrementsTheNumberOfSoldPropertiesThisYear() {
        propertyStatisticsCollector.applyPropertySale(propertyMock);
        verify(statisticsRepositoryMock).persist(PropertyStatisticsCollector.NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME,
                SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR + 1);
    }

    @Test
    public void applyingPropertySaleDecrementsTheNumberOfPropertiesForSaleInTheCorrespondingCategory() {
        propertyStatisticsCollector.applyPropertySale(propertyMock);

        assertEquals(SAMPLE_NUMBER_OF_CONDOS_FOR_SALE - 1, (int) numberOfPropertiesForSale.get(PropertyType.CONDO_LOFT));
        verify(statisticsRepositoryMock).persist(PropertyStatisticsCollector.NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME,
                numberOfPropertiesForSale);
    }

    @Test
    public void applyingPropertyForSaleIncrementsTheNumberOfPropertiesForSaleInTheCorrespondingCategory() {
        propertyStatisticsCollector.applyPropertyForSale(propertyMock);

        assertEquals(SAMPLE_NUMBER_OF_CONDOS_FOR_SALE + 1, (int) numberOfPropertiesForSale.get(PropertyType.CONDO_LOFT));
        verify(statisticsRepositoryMock).persist(PropertyStatisticsCollector.NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME,
                numberOfPropertiesForSale);
    }

    @Test
    public void gettingTheStatisticsCorrectlyCreatesTheStatisticsDTO() {
        PropertyStatistics propertyStatistics = propertyStatisticsCollector.getStatistics();

        assertEquals(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR, propertyStatistics.getNumberOfSoldPropertiesThisYear());
        assertSame(numberOfPropertiesForSale, propertyStatistics.getNumberOfPropertiesForSale());
    }
}
