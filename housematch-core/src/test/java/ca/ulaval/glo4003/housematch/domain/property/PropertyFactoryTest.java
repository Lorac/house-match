package ca.ulaval.glo4003.housematch.domain.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class PropertyFactoryTest {
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(5541);
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.FARM;

    private Address addressMock;
    private PropertyObserver propertyObserverMock;
    private List<PropertyObserver> propertyObservers = new ArrayList<>();

    private PropertyFactory propertyFactory;

    @Before
    public void init() {
        initMocks();
        propertyObservers.add(propertyObserverMock);
        propertyFactory = new PropertyFactory(propertyObservers);
    }

    private void initMocks() {
        addressMock = mock(Address.class);
        propertyObserverMock = mock(PropertyObserver.class);
    }

    @Test
    public void propertyFactoryCreatesPropertyWithTheSpecifiedAttributes() {
        Property property = propertyFactory.createProperty(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE);

        assertEquals(SAMPLE_PROPERTY_TYPE, property.getPropertyType());
        assertEquals(addressMock, property.getAddress());
        assertEquals(SAMPLE_SELLING_PRICE, property.getSellingPrice());
    }

    @Test
    public void propertyFactoryRegistersTheSharedObserverToTheProperty() {
        Property property = propertyFactory.createProperty(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE);
        assertTrue(property.isObserverRegistered(propertyObserverMock));
    }
}
