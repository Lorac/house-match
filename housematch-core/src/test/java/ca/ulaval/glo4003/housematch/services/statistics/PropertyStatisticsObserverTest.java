package ca.ulaval.glo4003.housematch.services.statistics;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class PropertyStatisticsObserverTest {

    private PropertyStatisticsService propertyStatisticsServiceMock;
    private Property propertyMock;
    private PropertyStatisticsObserver propertyStatisticsObserver;

    @Before
    public void init() {
        propertyStatisticsServiceMock = mock(PropertyStatisticsService.class);
        propertyMock = mock(Property.class);
        propertyStatisticsObserver = new PropertyStatisticsObserver(propertyStatisticsServiceMock);
    }

    @Test
    public void newPropertyForSaleStatusChangeFiresTheProperEventOfTheStatisticsService() {
        propertyStatisticsObserver.propertyStatusChanged(propertyMock, PropertyStatus.FOR_SALE);
        verify(propertyStatisticsServiceMock).processNewPropertyForSale(propertyMock);
    }

    @Test
    public void propertySaleStatusChangeFiresTheProperEventOfTheStatisticsService() {
        propertyStatisticsObserver.propertyStatusChanged(propertyMock, PropertyStatus.SOLD);
        verify(propertyStatisticsServiceMock).processPropertySale(propertyMock);
    }

}
