package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class PropertyPhotoObservableTest {

    private static final PropertyPhotoStatus SAMPLE_PROPERTY_PHOTO_STATUS = PropertyPhotoStatus.REJECTED;

    private PropertyPhotoObservable propertyPhotoObservable;

    private PropertyPhoto propertyPhotoMock;
    private PropertyPhotoObserver propertyPhotoObserverMock;
    private PropertyPhotoObserver anotherPropertyPhotoObserverMock;

    @Before
    public void init() {
        initMocks();
        propertyPhotoObservable = new PropertyPhotoObservable();
        registerObservers();
    }

    private void initMocks() {
        propertyPhotoMock = mock(PropertyPhoto.class);
        propertyPhotoObserverMock = mock(PropertyPhotoObserver.class);
        anotherPropertyPhotoObserverMock = mock(PropertyPhotoObserver.class);
    }

    private void registerObservers() {
        propertyPhotoObservable.registerObserver(propertyPhotoObserverMock);
        propertyPhotoObservable.registerObserver(anotherPropertyPhotoObserverMock);
    }

    @Test
    public void registeringAnObserverRegistersTheObserver() {
        assertTrue(propertyPhotoObservable.isObserverRegistered(propertyPhotoObserverMock));
    }

    @Test
    public void changingPropertyPhotoStatusNotifiesAllTheObservers() {
        propertyPhotoObservable.propertyPhotoStatusChanged(propertyPhotoMock, SAMPLE_PROPERTY_PHOTO_STATUS);

        verify(propertyPhotoObserverMock).propertyPhotoStatusChanged(propertyPhotoMock, SAMPLE_PROPERTY_PHOTO_STATUS);
        verify(anotherPropertyPhotoObserverMock).propertyPhotoStatusChanged(propertyPhotoMock, SAMPLE_PROPERTY_PHOTO_STATUS);
    }

    @Test
    public void unregisteringAnObserverUnregistersTheObserver() {
        propertyPhotoObservable.unregisterObserver(propertyPhotoObserverMock);
        assertFalse(propertyPhotoObservable.isObserverRegistered(propertyPhotoObserverMock));
    }

    @Test
    public void unregisteringAnObserverStopsNotifyingThatObserver() {
        propertyPhotoObservable.unregisterObserver(propertyPhotoObserverMock);

        propertyPhotoObservable.propertyPhotoStatusChanged(propertyPhotoMock, SAMPLE_PROPERTY_PHOTO_STATUS);

        verify(propertyPhotoObserverMock, never()).propertyPhotoStatusChanged(propertyPhotoMock, SAMPLE_PROPERTY_PHOTO_STATUS);
        verify(anotherPropertyPhotoObserverMock).propertyPhotoStatusChanged(propertyPhotoMock, SAMPLE_PROPERTY_PHOTO_STATUS);
    }
}
