package ca.ulaval.glo4003.housematch.domain.property;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public class PropertyFactoryTest {
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(5541);
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.FARM;

    private Address addressMock;

    private PropertyFactory propertyFactory;

    @Before
    public void init() {
        addressMock = mock(Address.class);
        propertyFactory = new PropertyFactory();
    }

    @Test
    public void propertyFactoryCreatesPropertyWithTheSpecifiedAttributes() {
        Property property = propertyFactory.getProperty(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE);

        assertEquals(SAMPLE_PROPERTY_TYPE, property.getPropertyType());
        assertEquals(addressMock, property.getAddress());
        assertEquals(SAMPLE_SELLING_PRICE, property.getSellingPrice());
    }
}
