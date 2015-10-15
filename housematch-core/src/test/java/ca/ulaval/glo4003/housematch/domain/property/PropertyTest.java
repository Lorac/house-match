package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PropertyTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final PropertyType ANOTHER_SAMPLE_PROPERTY_TYPE = PropertyType.LOT;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final BigDecimal ANOTHER_SAMPLE_SELLING_PRICE = BigDecimal.valueOf(4535);
    private static final Object SAMPLE_OBJECT = new Object();
    private Address addressMock;

    private Property property;
    private PropertyDetails propertyDetailsMock;

    @Before
    public void init() throws Exception {
        addressMock = mock(Address.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        property = new Property(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE);
    }

    @Test
    public void propertiesWithTheSameAddressShouldBeConsideredAsEqual() {
        Property anotherProperty = new Property(ANOTHER_SAMPLE_PROPERTY_TYPE, addressMock, ANOTHER_SAMPLE_SELLING_PRICE);
        assertTrue(property.equals(anotherProperty));
    }

    @Test
    public void propertiesWithDifferentAddressesShouldBeConsideredAsDifferent() {
        Address anotherAddressMock = mock(Address.class);
        Property anotherProperty = new Property(SAMPLE_PROPERTY_TYPE, anotherAddressMock, SAMPLE_SELLING_PRICE);

        assertFalse(property.equals(anotherProperty));
    }

    @Test
    public void propertiesWithTheSameAddressShouldHaveTheSameHashCode() {
        Property anotherProperty = new Property(ANOTHER_SAMPLE_PROPERTY_TYPE, addressMock, ANOTHER_SAMPLE_SELLING_PRICE);
        assertEquals(property.hashCode(), anotherProperty.hashCode());
    }

    @Test
    public void propertiesWithDifferentAddressesShouldNotHaveTheSameHashCode() {
        Address anotherAddressMock = mock(Address.class);
        Property anotherProperty = new Property(SAMPLE_PROPERTY_TYPE, anotherAddressMock, SAMPLE_SELLING_PRICE);
        assertNotEquals(property.hashCode(), anotherProperty.hashCode());
    }

    @Test
    public void propertyComparedWithItselfShouldBeConsideredAsEqual() {
        assertTrue(property.equals(property));
    }

    @Test
    public void propertyComparedWithAnotherObjectShouldNotBeConsideredAsEqual() {
        assertFalse(property.equals(SAMPLE_OBJECT));
    }

    @Test
    public void settingThePropertyTypeSetsTheSpecifiedPropertyType() {
        property.setPropertyType(ANOTHER_SAMPLE_PROPERTY_TYPE);
        assertEquals(ANOTHER_SAMPLE_PROPERTY_TYPE, property.getPropertyType());
    }

    @Test
    public void settingTheAddressSetsTheSpecifiedAddress() {
        Address anotherAddressMock = mock(Address.class);
        property.setAddress(anotherAddressMock);
        assertEquals(anotherAddressMock, property.getAddress());
    }

    @Test
    public void settingTheSellingPriceSetsTheSpecifiedSellingPrice() {
        property.setSellingPrice(ANOTHER_SAMPLE_SELLING_PRICE);
        assertEquals(ANOTHER_SAMPLE_SELLING_PRICE, property.getSellingPrice());
    }

    @Test
    public void settingThePropertyDetailsSetsTheSpecifiedPropertyDetails() {
        property.setPropertyDetails(propertyDetailsMock);
        assertEquals(propertyDetailsMock, property.getPropertyDetails());
    }
}