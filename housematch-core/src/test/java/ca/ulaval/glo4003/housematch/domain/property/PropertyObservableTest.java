package ca.ulaval.glo4003.housematch.domain.property;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

public class PropertyObservableTest {

    private static final PropertyStatus SAMPLE_PROPERTY_STATUS = PropertyStatus.FOR_SALE;

    private PropertyObservable propertyObservable;

    private Property propertyMock;
    private PropertyPhoto propertyPhotoMock;
    private PropertyDetails propertyDetailsMock;
    private PropertyObserver propertyObserverMock;
    private PropertyObserver anotherPropertyObserverMock;

    @Before
    public void init() {
        initMocks();
        propertyObservable = new PropertyObservable();
        registerObservers();
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        propertyPhotoMock = mock(PropertyPhoto.class);
        propertyObserverMock = mock(PropertyObserver.class);
        anotherPropertyObserverMock = mock(PropertyObserver.class);
    }

    private void registerObservers() {
        propertyObservable.registerObserver(propertyObserverMock);
        propertyObservable.registerObserver(anotherPropertyObserverMock);
    }

    @Test
    public void registeringAnObserverRegistersTheObserver() {
        assertTrue(propertyObservable.isObserverRegistered(propertyObserverMock));
    }

    @Test
    public void changingPropertyStatusNotifiesAllTheObservers() {
        propertyObservable.propertyStatusChanged(propertyMock, SAMPLE_PROPERTY_STATUS);

        verify(propertyObserverMock).propertyStatusChanged(propertyMock, SAMPLE_PROPERTY_STATUS);
        verify(anotherPropertyObserverMock).propertyStatusChanged(propertyMock, SAMPLE_PROPERTY_STATUS);
    }

    @Test
    public void changingPropertyDetailsNotifiesAllTheObservers() {
        propertyObservable.propertyDetailsChanged(propertyMock, propertyDetailsMock);

        verify(propertyObserverMock).propertyDetailsChanged(propertyMock, propertyDetailsMock);
        verify(anotherPropertyObserverMock).propertyDetailsChanged(propertyMock, propertyDetailsMock);
    }

    @Test
    public void rejectionOfPhotoNotifiesAllTheObservers() {
        propertyObservable.propertyPhotoRejected(propertyMock, propertyPhotoMock);

        verify(propertyObserverMock).propertyPhotoRejected(propertyMock, propertyPhotoMock);
        verify(anotherPropertyObserverMock).propertyPhotoRejected(propertyMock, propertyPhotoMock);
    }

    @Test
    public void unregisteringAnObserverUnregistersTheObserver() {
        propertyObservable.unregisterObserver(propertyObserverMock);
        assertFalse(propertyObservable.isObserverRegistered(propertyObserverMock));
    }

    @Test
    public void unregisteringAnObserverStopsNotifyingThatObserver() {
        propertyObservable.unregisterObserver(propertyObserverMock);

        propertyObservable.propertyStatusChanged(propertyMock, SAMPLE_PROPERTY_STATUS);

        verify(propertyObserverMock, never()).propertyStatusChanged(propertyMock, SAMPLE_PROPERTY_STATUS);
        verify(anotherPropertyObserverMock).propertyStatusChanged(propertyMock, SAMPLE_PROPERTY_STATUS);
    }
}
