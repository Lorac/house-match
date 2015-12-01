package ca.ulaval.glo4003.housematch.services.notification;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class PropertyNotificationObserver implements PropertyObserver {

    private static final String PROPERTY_PUT_UP_FOR_SALE_EVENT_DESCRIPTION = "A new property has been put up for sale: %s.";

    private NotificationService notificationService;

    public PropertyNotificationObserver(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        if (newStatus == PropertyStatus.FOR_SALE) {
            Notification notification = createPropertyPutUpForSaleNotification(property);
            notificationService.notifyAllUsers(notification);
        }
    }

    private Notification createPropertyPutUpForSaleNotification(Property property) {
        String eventDescription = String.format(PROPERTY_PUT_UP_FOR_SALE_EVENT_DESCRIPTION, property.toString());
        return new Notification(NotificationType.PROPERTY_PUT_UP_FOR_SALE, eventDescription);
    }

    @Override
    public void propertyDetailsChanged(Property property, PropertyDetails newPropertyDetails) {
        // Event intentionally ignored.
    }

}
