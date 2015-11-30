package ca.ulaval.glo4003.housematch.domain.notification;

public enum NotificationInterval {
    NEVER("Never"),
    IMMEDIATELY("Immediately"),
    DAILY("Daily", 24L * 60L * 60L * 1000L),
    WEEKLY("Weekly", 7L * 24L * 60L * 60L * 1000L);

    private String displayName;
    private Long intervalInMilliseconds = null;

    NotificationInterval(final String displayName) {
        this.displayName = displayName;
    }

    NotificationInterval(final String displayName, final Long intervalInMilliseconds) {
        this(displayName);
        this.intervalInMilliseconds = intervalInMilliseconds;
    }

    public Long getIntervalInMilliseconds() {
        return intervalInMilliseconds;
    }

    public String getDisplayName() {
        return displayName;
    }
}
