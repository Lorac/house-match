package ca.ulaval.glo4003.housematch.web.viewmodels;

import ca.ulaval.glo4003.housematch.domain.notification.NotificationInterval;

public class NotificationSettingsFormViewModel extends ViewModel {
    public static final String NAME = "notificationSettingsForm";

    private NotificationInterval propertyPutUpForSaleNotificationInterval;
    private NotificationInterval favoritePropertyModificationNotificationInterval;

    @Override
    public String getName() {
        return NAME;
    }

    public NotificationInterval getPropertyPutUpForSaleNotificationInterval() {
        return propertyPutUpForSaleNotificationInterval;
    }

    public void setPropertyPutUpForSaleNotificationInterval(NotificationInterval propertyAddedForSaleNotificationInterval) {
        this.propertyPutUpForSaleNotificationInterval = propertyAddedForSaleNotificationInterval;
    }

    public NotificationInterval getFavoritePropertyModificationNotificationInterval() {
        return favoritePropertyModificationNotificationInterval;
    }

    public void setFavoritePropertyModificationNotificationInterval(NotificationInterval favoritePropertyModificationNotificationInterval) {
        this.favoritePropertyModificationNotificationInterval = favoritePropertyModificationNotificationInterval;
    }
}
