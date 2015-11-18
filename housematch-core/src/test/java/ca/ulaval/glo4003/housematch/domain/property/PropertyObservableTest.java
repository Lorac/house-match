package ca.ulaval.glo4003.housematch.domain.property;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class PropertyObservableTest {

    private static final PropertyStatus SAMPLE_PROPERTY_STATUS = PropertyStatus.FOR_SALE;

    private PropertyObservable propertyObservable;

    private Property propertyMock;
    private PropertyObserver propertyObserverMock;
    private PropertyObserver anotherPropertyObserverMock;

    @Before
    public void init() {
        propertyObserverMock = mock(PropertyObserver.class);
        anotherPropertyObserverMock = mock(PropertyObserver.class);
        propertyObservable = new PropertyObservable();
        registerObservers();
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