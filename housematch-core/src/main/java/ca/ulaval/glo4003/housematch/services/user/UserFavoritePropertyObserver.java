package ca.ulaval.glo4003.housematch.services.user;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;
import ca.ulaval.glo4003.housematch.domain.user.User;

public class UserFavoritePropertyObserver implements PropertyObserver {

    private static final String FAVORITE_PROPERTY_CHANGED_EVENT_DESCRIPTION = "The details of a property you favorited (%s) have changed.";

    private NotificationFactory notificationFactory;
    private UserService userService;
    private User user;

    public UserFavoritePropertyObserver(final User user, final UserService userService) {
        this(user, userService, new NotificationFactory());
    }

    public UserFavoritePropertyObserver(final User user, final UserService userService, final NotificationFactory notificationFactory) {
        this.user = user;
        this.userService = userService;
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
        String eventDescription = String.format(FAVORITE_PROPERTY_CHANGED_EVENT_DESCRIPTION, property.toString());
        Notification notification = notificationFactory.createNotification(NotificationType.FAVORITE_PROPERTY_MODIFIED, eventDescription);
        userService.notifyUser(user, notification);
    }

}
