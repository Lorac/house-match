package ca.ulaval.glo4003.housematch.domain.notification;

public enum NotificationType {
    PROPERTY_PUT_UP_FOR_SALE(NotificationInterval.NEVER, "A new property has been put up for sale: %s."),
    FAVORITE_PROPERTY_CHANGED(NotificationInterval.NEVER, "The details of a property you favorited (%s) have changed."),
    PROPERTY_PHOTO_REJECTED(NotificationInterval.IMMEDIATELY, "The photo '%s' of your property for sale (%s) has been rejected.");

    private NotificationInterval defaultInterval;
    private String defaultDescriptionFormat;

    NotificationType(final NotificationInterval defaultInterval, final String defaultDescriptionFormat) {
        this.defaultInterval = defaultInterval;
        this.defaultDescriptionFormat = defaultDescriptionFormat;
    }

    public NotificationInterval getDefaultNotificationInterval() {
        return defaultInterval;
    }

    public String formatDescription(Object... arguments) {
        return String.format(defaultDescriptionFormat, arguments);
    }

}
