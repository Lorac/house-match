package ca.ulaval.glo4003.housematch.services.statistics;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;

public class PropertyStatisticsServiceTest {

    private static final Integer SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR = 5;
    private static final Integer SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE = 2;
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.CONDO_LOFT;

    private PropertyStatistics propertyStatisticsMock;
    private Property propertyMock;

    private PropertyStatisticsService propertyStatisticsService;

    @Before
    public void init() {
        initMocks();
        initStubs();
        propertyStatisticsService = new PropertyStatisticsService(propertyStatisticsMock);
    }

    private void initMocks() {
        propertyStatisticsMock = mock(PropertyStatistics.class);
        propertyMock = mock(Property.class);
    }

    public void initStubs() {
        when(propertyMock.getPropertyType()).thenReturn(SAMPLE_PROPERTY_TYPE);
        when(propertyStatisticsMock.getNumberOfSoldPropertiesThisYear()).thenReturn(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR);
        when(propertyStatisticsMock.getNumberOfPropertiesForSale(SAMPLE_PROPERTY_TYPE)).thenReturn(SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE);
    }

    @Test
    public void processingPropertySaleIncrementsTheNumberOfSoldPropertiesThisYear() {
        propertyStatisticsService.processPropertySale(propertyMock);
        verify(propertyStatisticsMock).setNumberOfSoldPropertiesThisYear(SAMPLE_NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR + 1);
    }

    @Test
    public void processingPropertySaleDecrementsTheNumberOfPropertiesForSaleInTheCorrespondingCategory() {
        propertyStatisticsService.processPropertySale(propertyMock);
        verify(propertyStatisticsMock).setNumberOfPropertiesForSale(SAMPLE_PROPERTY_TYPE, SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE - 1);
    }

    @Test
    public void processingNewPropertyForSaleIncrementsTheNumberOfPropertiesForSaleInTheCorrespondingCategory() {
        propertyStatisticsService.processNewPropertyForSale(propertyMock);
        verify(propertyStatisticsMock).setNumberOfPropertiesForSale(SAMPLE_PROPERTY_TYPE, SAMPLE_NUMBER_OF_PROPERTIES_FOR_SALE + 1);
    }

    @Test
    public void gettingTheStatisticsGetsTheStatistics() {
        assertSame(propertyStatisticsMock, propertyStatisticsService.getStatistics());
    }

}
