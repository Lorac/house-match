package ca.ulaval.glo4003.housematch.services.notification;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class PropertyNotificationObserverTest {

    private Property propertyMock;
    private Notification notificationMock;
    private NotificationFactory notificationFactoryMock;
    private NotificationService notificationServiceMock;

    private PropertyNotificationObserver propertyNotificationObserver;

    @Before
    public void init() {
        initMocks();
        initStubs();
        propertyNotificationObserver = new PropertyNotificationObserver(notificationServiceMock, notificationFactoryMock);
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        notificationFactoryMock = mock(NotificationFactory.class);
        notificationServiceMock = mock(NotificationService.class);
        notificationMock = mock(Notification.class);
    }

    private void initStubs() {
        when(notificationFactoryMock.createNotification(any(NotificationType.class), anyString())).thenReturn(notificationMock);
    }

    @Test
    public void propertyStatusChangedToForSaleStatusNotifiesAllTheUsersUsingTheNotificationService() {
        propertyNotificationObserver.propertyStatusChanged(propertyMock, PropertyStatus.FOR_SALE);
        verify(notificationServiceMock).notifyAllUsers(notificationMock);
    }

    @Test
    public void propertyStatusChangedToAnotherStatusThanForSaleDoesNotCreateAnyNotification() {
        propertyNotificationObserver.propertyStatusChanged(propertyMock, PropertyStatus.SOLD);
        verify(notificationServiceMock, never()).notifyAllUsers(notificationMock);
    }

}
