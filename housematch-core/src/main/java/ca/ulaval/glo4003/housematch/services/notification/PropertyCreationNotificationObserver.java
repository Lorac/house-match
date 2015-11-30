package ca.ulaval.glo4003.housematch.services.notification;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class PropertyCreationNotificationObserver implements PropertyObserver {

    private static final String PROPERTY_CREATION_EVENT_DESCRIPTION = "A new property has been put up for sale: %s.";

    private NotificationFactory notificationFactory;
    private NotificationService notificationService;

    public PropertyCreationNotificationObserver(final NotificationFactory notificationFactory, final NotificationService notificationService) {
        this.notificationFactory = notificationFactory;
        this.notificationService = notificationService;
    }

    @Override
    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        if (newStatus == PropertyStatus.FOR_SALE) {
            Notification notification = createPropertAddedForSaleNotification(property);
            notificationService.notifyAllUsers(notification);
        }
    }

    private Notification createPropertAddedForSaleNotification(Property property) {
        String eventDescription = String.format(PROPERTY_CREATION_EVENT_DESCRIPTION, property.toString());
        return notificationFactory.createNotification(NotificationType.PROPERTY_ADDED_FOR_SALE, eventDescription);
    }

    @Override
    public void propertyDetailsChanged(Property property, PropertyDetails newPropertyDetails) {
        // Event intentionally ignored.
    }

}
