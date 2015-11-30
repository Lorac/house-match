package ca.ulaval.glo4003.housematch.statistics.property;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class PropertyStatisticsObserverTest {

    private PropertyStatisticsCollector propertyStatisticsCollectorMock;
    private Property propertyMock;
    private PropertyStatisticsObserver propertyStatisticsObserver;

    @Before
    public void init() {
        propertyStatisticsCollectorMock = mock(PropertyStatisticsCollector.class);
        propertyMock = mock(Property.class);
        propertyStatisticsObserver = new PropertyStatisticsObserver(propertyStatisticsCollectorMock);
    }

    @Test
    public void newPropertyForSaleStatusChangeFiresTheProperEventOfTheStatisticsCollector() {
        propertyStatisticsObserver.propertyStatusChanged(propertyMock, PropertyStatus.FOR_SALE);
        verify(propertyStatisticsCollectorMock).processNewPropertyForSale(propertyMock);
    }

    @Test
    public void propertySaleStatusChangeFiresTheProperEventOfTheStatisticsCollector() {
        propertyStatisticsObserver.propertyStatusChanged(propertyMock, PropertyStatus.SOLD);
        verify(propertyStatisticsCollectorMock).processPropertySale(propertyMock);
    }

}
