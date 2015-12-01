package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class UserFavoritePropertyObserver implements PropertyObserver {

    private static final String FAVORITE_PROPERTY_CHANGED_EVENT_DESCRIPTION = "The details of a property you favorited (%s) have changed.";

    private NotificationFactory notificationFactory;
    private User user;

    public UserFavoritePropertyObserver() {
        this.notificationFactory = new NotificationFactory();
    }

    public UserFavoritePropertyObserver(final NotificationFactory notificationFactory) {
        this.notificationFactory = notificationFactory;
    }

    public UserFavoritePropertyObserver(final User user) {
        this.user = user;
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
        String eventDescription = String.format(FAVORITE_PROPERTY_CHANGED_EVENT_DESCRIPTION, property.toString());
        Notification notification = notificationFactory.createNotification(NotificationType.FAVORITE_PROPERTY_MODIFIED, eventDescription);
        user.notify(notification);
    }

}
