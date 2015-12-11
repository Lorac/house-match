package ca.ulaval.glo4003.housematch.domain.notification;

public enum NotificationType {
    PROPERTY_PUT_UP_FOR_SALE(NotificationInterval.NEVER),
    FAVORITE_PROPERTY_CHANGED(NotificationInterval.NEVER),
    PROPERTY_PHOTO_REJECTED(NotificationInterval.IMMEDIATELY);

    NotificationInterval defaultInterval;

    NotificationType(final NotificationInterval defaultInterval) {
        this.defaultInterval = defaultInterval;
    }

    public NotificationInterval getDefaultNotificationInterval() {
        return defaultInterval;
    }
}
