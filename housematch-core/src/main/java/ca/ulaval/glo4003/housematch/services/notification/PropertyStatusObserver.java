package ca.ulaval.glo4003.housematch.services.notification;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationFactory;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

public class PropertyStatusObserver implements PropertyObserver {

    private NotificationService notificationService;
    private NotificationFactory notificationFactory;

    public PropertyStatusObserver(final NotificationService notificationService, final NotificationFactory notificationFactory) {
        this.notificationService = notificationService;
        this.notificationFactory = notificationFactory;
    }

    @Override
    public void propertyStatusChanged(Object sender, PropertyStatus newStatus) {
        if (newStatus == PropertyStatus.FOR_SALE) {
            Notification notification = createPropertyPutUpForSaleNotification((Property) sender);
            notificationService.notifyAllUsers(notification);
        }
    }

    private Notification createPropertyPutUpForSaleNotification(Property property) {
        String notificationDescription = NotificationType.PROPERTY_PUT_UP_FOR_SALE.formatDescription(property);
        return notificationFactory.createNotification(NotificationType.PROPERTY_PUT_UP_FOR_SALE, notificationDescription);
    }

    @Override
    public void propertyDetailsChanged(Object sender, PropertyDetails newPropertyDetails) {
        // Event intentionally ignored.
    }

    @Override
    public void propertyPhotoRejected(Object sender, PropertyPhoto propertyPhoto) {
        // Event intentionally ignored.
    }

}
