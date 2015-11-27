package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PropertyTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final PropertyType ANOTHER_SAMPLE_PROPERTY_TYPE = PropertyType.LOT;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final BigDecimal ANOTHER_SAMPLE_SELLING_PRICE = BigDecimal.valueOf(4535);
    private static final PropertyStatus SAMPLE_STATUS = PropertyStatus.FOR_SALE;
    private static final ZonedDateTime SAMPLE_DATE = ZonedDateTime.now();
    private static final Integer SAMPLE_VIEW_COUNT = 350;
    private static final Object SAMPLE_OBJECT = new Object();

    private Property property;
    private PropertyDetails propertyDetailsMock;
    private PropertyObserver propertyObserverMock;
    private Address addressMock;

    @Before
    public void init() throws Exception {
        initMocks();
        property = new Property(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE, propertyDetailsMock);
        property.registerObserver(propertyObserverMock);
    }

    private void initMocks() {
        addressMock = mock(Address.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        propertyObserverMock = mock(PropertyObserver.class);
    }

    @Test
    public void propertiesWithTheSameAddressShouldBeConsideredAsEqual() {
        Property anotherProperty = new Property(ANOTHER_SAMPLE_PROPERTY_TYPE, addressMock, ANOTHER_SAMPLE_SELLING_PRICE,
                propertyDetailsMock);
        assertTrue(property.equals(anotherProperty));
    }

    @Test
    public void propertiesWithDifferentAddressesShouldBeConsideredAsDifferent() {
        Address anotherAddressMock = mock(Address.class);
        Property anotherProperty = new Property(SAMPLE_PROPERTY_TYPE, anotherAddressMock, SAMPLE_SELLING_PRICE, propertyDetailsMock);

        assertFalse(property.equals(anotherProperty));
    }

    @Test
    public void propertiesWithTheSameAddressShouldHaveTheSameHashCode() {
        Property anotherProperty = new Property(ANOTHER_SAMPLE_PROPERTY_TYPE, addressMock, ANOTHER_SAMPLE_SELLING_PRICE,
                propertyDetailsMock);
        assertEquals(property.hashCode(), anotherProperty.hashCode());
    }

    @Test
    public void propertiesWithDifferentAddressesShouldNotHaveTheSameHashCode() {
        Address anotherAddressMock = mock(Address.class);
        Property anotherProperty = new Property(SAMPLE_PROPERTY_TYPE, anotherAddressMock, SAMPLE_SELLING_PRICE, propertyDetailsMock);
        assertNotEquals(property.hashCode(), anotherProperty.hashCode());
    }

    @Test
    public void propertyHasNoViewsOnCreation() {
        assertTrue(property.getViewCount() == 0);
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
    public void gettingThePropertyTypeGetsThePropertyType() {
        assertEquals(SAMPLE_PROPERTY_TYPE, property.getPropertyType());
    }

    @Test
    public void gettingTheAddressGetsTheAddress() {
        assertEquals(addressMock, property.getAddress());
    }

    @Test
    public void gettingTheSellingPriceGetsTheSellingPrice() {
        assertEquals(SAMPLE_SELLING_PRICE, property.getSellingPrice());
    }

    @Test
    public void settingThePropertyDetailsSetsTheSpecifiedPropertyDetails() {
        property.setPropertyDetails(propertyDetailsMock);
        assertEquals(propertyDetailsMock, property.getPropertyDetails());
    }

    @Test
    public void settingTheStatusSetsTheSpecifiedStatus() {
        property.setStatus(SAMPLE_STATUS);
        assertEquals(SAMPLE_STATUS, property.getStatus());
    }

    @Test
    public void settingTheCreationDateSetsTheSpecifiedCreationDate() {
        property.setCreationDate(SAMPLE_DATE);
        assertEquals(SAMPLE_DATE, property.getCreationDate());
    }

    @Test
    public void settingTheViewCountSetsTheSpecifiedViewCount() {
        property.setViewCount(SAMPLE_VIEW_COUNT);
        assertEquals(SAMPLE_VIEW_COUNT, property.getViewCount());
    }

    @Test
    public void incrementingThePropetyViewCountIncrementsThePropertyViewCountByOne() {
        property.setViewCount(SAMPLE_VIEW_COUNT);
        int newViewCount = property.incrementViewCount();
        assertTrue(newViewCount - SAMPLE_VIEW_COUNT == 1);
    }

    @Test
    public void markingThePropertyForSaleMarksThePropertyForSale() {
        property.markForSale();
        assertTrue(property.isForSale());
    }

    @Test
    public void markingThePropertyForSaleNotifiesTheObservers() {
        property.markForSale();
        verify(propertyObserverMock).propertyStatusChanged(property, PropertyStatus.FOR_SALE);
    }

    @Test
    public void markingThePropertyAsSoldMarksThePropertyAsSold() {
        property.markAsSold();
        assertFalse(property.isForSale());
    }

    @Test
    public void markingThePropertyAsSoldNotifiesTheObservers() {
        property.markAsSold();
        verify(propertyObserverMock).propertyStatusChanged(property, PropertyStatus.SOLD);
    }
}