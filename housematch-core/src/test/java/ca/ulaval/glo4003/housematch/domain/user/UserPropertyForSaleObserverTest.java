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
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

public class UserPropertyForSaleObserverTest {

    private Property propertyMock;
    private PropertyPhoto propertyPhotoMock;
    private User userMock;
    private Notification notificationMock;
    private NotificationFactory notificationFactoryMock;

    private UserPropertyForSaleObserver userPropertyForSaleObserver;

    @Before
    public void init() {
        propertyMock = mock(Property.class);
        propertyPhotoMock = mock(PropertyPhoto.class);
        userMock = mock(User.class);
        notificationFactoryMock = mock(NotificationFactory.class);
        notificationMock = mock(Notification.class);
        userPropertyForSaleObserver = new UserPropertyForSaleObserver(userMock, notificationFactoryMock);
    }

    @Test
    public void propertyPhotoRejectedChangeNotifiesTheUser() {
        when(notificationFactoryMock.createNotification(any(NotificationType.class), anyString())).thenReturn(notificationMock);
        userPropertyForSaleObserver.propertyPhotoRejected(propertyMock, propertyPhotoMock);
        verify(userMock).notify(notificationMock);
    }

}
