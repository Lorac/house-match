package ca.ulaval.glo4003.housematch.domain.user;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class UserFavoritePropertyObserverTest {

    private static final PropertyStatus SAMPLE_PROPERTY_STATUS = PropertyStatus.CREATED;

    private Property propertyMock;
    private PropertyDetails propertyDetailsMock;
    private User userMock;
    private Notification notificationMock;
    private NotificationFactory notificationFactoryMock;

    private UserFavoritePropertyObserver userFavoritePropertyObserver;

    @Before
    public void init() {
        propertyMock = mock(Property.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        userMock = mock(User.class);
        notificationFactoryMock = mock(NotificationFactory.class);
        notificationMock = mock(Notification.class);
        userFavoritePropertyObserver = new UserFavoritePropertyObserver(userMock, notificationFactoryMock);
    }

    @Test
    public void propertyStatusChangeNotifiesTheUser() {
        when(notificationFactoryMock.createNotification(any(NotificationType.class), anyString())).thenReturn(notificationMock);
        userFavoritePropertyObserver.propertyStatusChanged(propertyMock, SAMPLE_PROPERTY_STATUS);
        verify(userMock).notify(notificationMock);
    }

    @Test
    public void propertyDetailsChangeNotifiesTheUser() {
        when(notificationFactoryMock.createNotification(any(NotificationType.class), anyString())).thenReturn(notificationMock);
        userFavoritePropertyObserver.propertyDetailsChanged(propertyMock, propertyDetailsMock);
        verify(userMock).notify(notificationMock);
    }

}
