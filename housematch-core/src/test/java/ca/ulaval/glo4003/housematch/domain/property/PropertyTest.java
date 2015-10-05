package ca.ulaval.glo4003.housematch.domain.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.streetaddress.StreetAddress;

public class PropertyTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final PropertyType ANOTHER_SAMPLE_PROPERTY_TYPE = PropertyType.LOT;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final BigDecimal ANOTHER_SAMPLE_SELLING_PRICE = BigDecimal.valueOf(4535);
    private static final Object SAMPLE_OBJECT = new Object();
    private StreetAddress streetAddressMock;

    private Property property;

    @Before
    public void init() throws Exception {
        streetAddressMock = mock(StreetAddress.class);
        property = new Property(SAMPLE_PROPERTY_TYPE, streetAddressMock, SAMPLE_SELLING_PRICE);
    }

    @Test
    public void propertiesWithTheSameStreetAddressShouldBeConsideredAsEqual() throws Exception {
        Property anotherProperty = new Property(ANOTHER_SAMPLE_PROPERTY_TYPE, streetAddressMock,
                ANOTHER_SAMPLE_SELLING_PRICE);
        assertTrue(property.equals(anotherProperty));
    }

    @Test
    public void propertiesWithDifferentStreetAddressesShouldBeConsideredAsDifferent() throws Exception {
        StreetAddress anotherStreetAddressMock = mock(StreetAddress.class);
        Property anotherProperty = new Property(SAMPLE_PROPERTY_TYPE, anotherStreetAddressMock, SAMPLE_SELLING_PRICE);

        assertFalse(property.equals(anotherProperty));
    }

    @Test
    public void propertiesWithTheSameStreetAddressShouldHaveTheSameHashCode() throws Exception {
        Property anotherProperty = new Property(ANOTHER_SAMPLE_PROPERTY_TYPE, streetAddressMock,
                ANOTHER_SAMPLE_SELLING_PRICE);
        assertEquals(property.hashCode(), anotherProperty.hashCode());
    }

    @Test
    public void propertiesWithDifferentStreetAddressesShouldNotHaveTheSameHashCode() throws Exception {
        StreetAddress anotherStreetAddressMock = mock(StreetAddress.class);
        Property anotherProperty = new Property(SAMPLE_PROPERTY_TYPE, anotherStreetAddressMock, SAMPLE_SELLING_PRICE);

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
    public void settingThePropertyTypeSetsTheSpecifiedPropertyType() throws Exception {
        property.setPropertyType(ANOTHER_SAMPLE_PROPERTY_TYPE);
        assertEquals(ANOTHER_SAMPLE_PROPERTY_TYPE, property.getPropertyType());
    }

    @Test
    public void settingTheStreetAddressSetsTheSpecifiedStreetAddress() throws Exception {
        StreetAddress anotherStreetAddressMock = mock(StreetAddress.class);
        property.setStreetAddress(anotherStreetAddressMock);
        assertEquals(anotherStreetAddressMock, property.getStreetAddress());
    }

    @Test
    public void settingTheSellingPriceSetsTheSpecifiedSellingPrice() throws Exception {
        property.setSellingPrice(ANOTHER_SAMPLE_SELLING_PRICE);
        assertEquals(ANOTHER_SAMPLE_SELLING_PRICE, property.getSellingPrice());
    }
}