package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class UserFavoritePropertyObserver implements PropertyObserver {

    User user;

    public UserFavoritePropertyObserver(final User user) {
        this.user = user;
    }

    @Override
    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        // Event intentionally ignored.
    }

    @Override
    public void propertyDetailsChanged(Property property, PropertyDetails newPropertyDetails) {
        String eventDescription = String.format("Details of a property you favorited ('%s') have changed.", property.toString());
        Notification notification = new Notification(NotificationType.FAVORITE_PROPERTY_MODIFICATION, eventDescription);
        user.notify(notification);
    }

}
