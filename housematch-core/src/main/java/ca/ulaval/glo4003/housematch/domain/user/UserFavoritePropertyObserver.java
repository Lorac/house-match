package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class UserFavoritePropertyObserver implements PropertyObserver {

    private NotificationFactory notificationFactory;
    private User user;

    public UserFavoritePropertyObserver(final User user, final NotificationFactory notificationFactory) {
        this.user = user;
        this.notificationFactory = notificationFactory;
    }

    @Override
    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        notifyUser(property);
    }

    @Override
    public void propertyDetailsChanged(Property property, PropertyDetails newPropertyDetails) {
        notifyUser(property);
    }

    private void notifyUser(Property property) {
        String eventDescription = String.format("The details of a property you favorited (%s) have changed.", property.toString());
        Notification notification = notificationFactory.createNotification(NotificationType.FAVORITE_PROPERTY_MODIFICATION,
                eventDescription);
        user.notify(notification);
    }

}
