package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

public class UserPropertyForSaleObserver implements PropertyObserver {

    private static final String PROPERTY_PHOTO_REJECTED_EVENT_DESCRIPTION
        = "The photo '%s' of your property for sale (%s) has been rejected.";

    private NotificationFactory notificationFactory;
    private User user;

    public UserPropertyForSaleObserver(final User user) {
        this(user, new NotificationFactory());
    }

    public UserPropertyForSaleObserver(final User user, final NotificationFactory notificationFactory) {
        this.user = user;
        this.notificationFactory = notificationFactory;
    }

    @Override
    public void propertyPhotoRejected(Property property, PropertyPhoto propertyPhoto) {
        String eventDescription = String.format(PROPERTY_PHOTO_REJECTED_EVENT_DESCRIPTION, propertyPhoto.toString(), property.toString());
        Notification notification = notificationFactory.createNotification(NotificationType.PROPERTY_PHOTO_REJECTED, eventDescription);
        user.notify(notification);
    }

    @Override
    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        // Event intentionally ignored.
    }

    @Override
    public void propertyDetailsChanged(Property property, PropertyDetails newPropertyDetails) {
        // Event intentionally ignored.
    }

}
