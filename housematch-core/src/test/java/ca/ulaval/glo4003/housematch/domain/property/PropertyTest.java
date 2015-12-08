package ca.ulaval.glo4003.housematch.domain.property;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoStatus;

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
    private PropertyPhoto propertyPhotoMock;
    private PropertyPhoto anotherPropertyPhotoMock;
    private List<BigDecimal> sellingPriceHistory = new ArrayList<>();
    private Set<PropertyPhoto> photos = new HashSet<>();

    @Before
    public void init() throws Exception {
        initMocks();
        sellingPriceHistory = new ArrayList<>();
        property = new Property(SAMPLE_PROPERTY_TYPE, addressMock, SAMPLE_SELLING_PRICE, propertyDetailsMock);
        property.registerObserver(propertyObserverMock);
        property.addPhoto(propertyPhotoMock);
    }

    private void initMocks() {
        addressMock = mock(Address.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        propertyObserverMock = mock(PropertyObserver.class);
        propertyPhotoMock = mock(PropertyPhoto.class);
        anotherPropertyPhotoMock = mock(PropertyPhoto.class);
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
    public void checkingIfPropertyIsOfTheSpecifiedTypeReturnsTrueWhenItIsOfTheSpecifiedType() {
        assertTrue(property.isOfType(SAMPLE_PROPERTY_TYPE));
    }

    @Test
    public void checkingIfPropertyIsOfTheSpecifiedTypeReturnsFalseWhenItIsNotOfTheSpecifiedType() {
        assertFalse(property.isOfType(ANOTHER_SAMPLE_PROPERTY_TYPE));
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
    public void checkingIfPropertyHasTheSpecifiedStatusReturnsTrueWhenItHasTheSpecifiedStatus() {
        property.setStatus(SAMPLE_STATUS);
        assertTrue(property.hasStatus(SAMPLE_STATUS));
    }

    @Test
    public void checkingIfPropertyHasTheSpecifiedStatusReturnsFalseWhenItHasNotTheSpecifiedStatus() {
        assertFalse(property.hasStatus(SAMPLE_STATUS));
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
    public void settingTheSellingPriceHistorySetsTheSpecifiedSellingPriceHistory() {
        property.setSellingPriceHistory(sellingPriceHistory);
        assertEquals(sellingPriceHistory, property.getSellingPriceHistory());
    }

    @Test
    public void settingThePhotosSetsTheSpecifiedPhotos() {
        property.setPhotos(photos);
        assertEquals(photos, property.getPhotos());
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

    @Test
    public void updatingThePropertyDetailsUpdatesThePropertyDetails() {
        property.updatePropertyDetails(propertyDetailsMock);
        assertEquals(propertyDetailsMock, property.getPropertyDetails());
    }

    @Test
    public void updatingThePropertyDetailsNotifiesTheObservers() {
        property.updatePropertyDetails(propertyDetailsMock);
        verify(propertyObserverMock).propertyDetailsChanged(property, propertyDetailsMock);
    }

    @Test
    public void propertyToStringReturnsTheAddressString() {
        assertEquals(addressMock.toString(), property.toString());
    }

    @Test
    public void updatingTheSellingPriceUpdatesTheSellingPrice() {
        property.updateSellingPrice(ANOTHER_SAMPLE_SELLING_PRICE);
        assertEquals(ANOTHER_SAMPLE_SELLING_PRICE, property.getSellingPrice());
    }

    @Test
    public void updatingTheSellingPriceAddsTheOldSellingPriceToTheHistory() {
        property.updateSellingPrice(ANOTHER_SAMPLE_SELLING_PRICE);
        assertThat(property.getSellingPriceHistory(), contains(SAMPLE_SELLING_PRICE));
    }

    @Test
    public void updatingTheSellingPriceUsingTheSameSellingPriceDoesNotAddTheOldSellingPriceToTheHistory() {
        property.updateSellingPrice(SAMPLE_SELLING_PRICE);
        assertThat(property.getSellingPriceHistory(), not(contains(SAMPLE_SELLING_PRICE)));
    }

    @Test
    public void addingPhotoRegisterItselfToThePropertyPhotoAsAnObserver() {
        verify(propertyPhotoMock).registerObserver(property);
    }

    @Test
    public void removingPhotoRemovesThePhotoFromTheProperty() throws Exception {
        property.removePhoto(propertyPhotoMock);
        assertThat(property.getPhotos(), not(hasItem(propertyPhotoMock)));
    }

    @Test
    public void rejectingPhotoRemovesThePhotoFromTheProperty() throws Exception {
        property.removePhoto(propertyPhotoMock);
        assertThat(property.getPhotos(), not(hasItem(propertyPhotoMock)));
    }

    @Test
    public void gettingPhotoByHashCodeReturnsThePhotoFromTheSpecifiedHashCode() throws Exception {
        PropertyPhoto returnedPropertyPhoto = property.getPhotoByHashCode(propertyPhotoMock.hashCode());
        assertSame(propertyPhotoMock, returnedPropertyPhoto);
    }

    @Test(expected = PropertyPhotoNotFoundException.class)
    public void gettingPhotoByHashCodeThrowsPropertyPhotoNotFoundExceptionWhenTheSpecifiedPhotoDoesNotExist() throws Exception {
        property.getPhotoByHashCode(anotherPropertyPhotoMock.hashCode());
    }

    @Test
    public void gettingApprovedPhotosReturnsApprovedPhotos() {
        when(propertyPhotoMock.isApproved()).thenReturn(true);
        assertThat(property.getApprovedPhotos(), hasItem(propertyPhotoMock));
    }

    @Test
    public void gettingApprovedPhotosDoesNotReturnUnapprovedPhotos() {
        when(propertyPhotoMock.isApproved()).thenReturn(false);
        assertThat(property.getApprovedPhotos(), not(hasItem(propertyPhotoMock)));
    }

    @Test
    public void gettingMainPhotoReturnsTheFirstApprovedPhoto() {
        when(propertyPhotoMock.isApproved()).thenReturn(false);
        when(anotherPropertyPhotoMock.isApproved()).thenReturn(true);
        property.addPhoto(anotherPropertyPhotoMock);

        Optional<PropertyPhoto> photo = property.getMainPhoto();

        assertSame(anotherPropertyPhotoMock, photo.get());
    }

    @Test
    public void propertyPhotoStatusChangeNotifiesTheObserversWhenChangedToRejected() throws Exception {
        property.propertyPhotoStatusChanged(propertyPhotoMock, PropertyPhotoStatus.REJECTED);
        verify(propertyObserverMock).propertyPhotoRejected(property, propertyPhotoMock);
    }

    @Test
    public void propertyPhotoStatusChangeDoesNotNotifyTheObserversWhenChangedToOtherThanRejected() throws Exception {
        property.propertyPhotoStatusChanged(propertyPhotoMock, PropertyPhotoStatus.APPROVED);
        verify(propertyObserverMock, never()).propertyPhotoRejected(property, propertyPhotoMock);
    }
}