package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.PropertyPhotoStatusObserver;

public class PropertyPhotoTest {

    private static final int SAMPLE_HASH_CODE = 2342;
    private static final int ANOTHER_SAMPLE_HASH_CODE = 23422;
    private static final PropertyPhotoStatus SAMPLE_PHOTO_STATUS = PropertyPhotoStatus.REJECTED;

    private PropertyPhoto propertyPhoto;
    private PropertyPhotoStatusObserver propertyPhotoStatusObserverMock;

    @Before
    public void init() {
        propertyPhotoStatusObserverMock = mock(PropertyPhotoStatusObserver.class);
        propertyPhoto = new PropertyPhoto(SAMPLE_HASH_CODE);
        propertyPhoto.registerObserver(propertyPhotoStatusObserverMock);
    }

    @Test
    public void settingTheStatusSetsTheStatus() {
        propertyPhoto.setStatus(SAMPLE_PHOTO_STATUS);
        assertEquals(SAMPLE_PHOTO_STATUS, propertyPhoto.getStatus());
    }

    @Test
    public void photoIsNotApprovedOnCreation() {
        assertFalse(propertyPhoto.isApproved());
    }

    @Test
    public void settingTheStatusToApprovedMakesThePhotoApproved() {
        propertyPhoto.setStatus(PropertyPhotoStatus.APPROVED);
        assertTrue(propertyPhoto.isApproved());
    }

    @Test
    public void settingTheStatusToAStatusOtherThanApprovedMakesThePhotoNotApproved() {
        propertyPhoto.setStatus(PropertyPhotoStatus.REJECTED);
        assertFalse(propertyPhoto.isApproved());
    }

    @Test
    public void updatingTheStatusUpdatesTheStatus() {
        propertyPhoto.updateStatus(SAMPLE_PHOTO_STATUS);
        assertEquals(SAMPLE_PHOTO_STATUS, propertyPhoto.getStatus());
    }

    @Test
    public void updatingTheStatusNotifiesTheObservers() {
        propertyPhoto.updateStatus(SAMPLE_PHOTO_STATUS);
        verify(propertyPhotoStatusObserverMock).propertyPhotoStatusChanged(propertyPhoto, SAMPLE_PHOTO_STATUS);
    }

    @Test
    public void hashCodeShouldHaveTheHashCodeSetDuringObjectConstruction() {
        assertEquals(SAMPLE_HASH_CODE, propertyPhoto.hashCode());
    }

    @Test
    public void photosWithTHeSameHashCodeShouldBeConsideredAsEquals() {
        PropertyPhoto anotherPropertyPhoto = new PropertyPhoto(SAMPLE_HASH_CODE);
        assertEquals(propertyPhoto, anotherPropertyPhoto);
    }

    @Test
    public void photosWithDifferentHashCodesShouldBeConsideredAsDifferent() {
        PropertyPhoto anotherPropertyPhoto = new PropertyPhoto(ANOTHER_SAMPLE_HASH_CODE);
        assertNotEquals(propertyPhoto, anotherPropertyPhoto);
    }
}
