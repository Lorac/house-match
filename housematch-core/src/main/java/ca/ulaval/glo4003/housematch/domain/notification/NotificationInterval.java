package ca.ulaval.glo4003.housematch.domain.notification;

public enum NotificationInterval {
    NONE,
    IMMEDIATELY,
    DAILY(24L * 60L * 60L * 1000L),
    WEEKLY(7L * 24L * 60L * 60L * 1000L);

    private Long intervalInMilliseconds;

    NotificationInterval() {
        this.intervalInMilliseconds = null;
    }

    NotificationInterval(final Long intervalInMilliseconds) {
        this.intervalInMilliseconds = intervalInMilliseconds;
    }

    public Long getIntervalInMilliseconds() {
        return intervalInMilliseconds;
    }
}
