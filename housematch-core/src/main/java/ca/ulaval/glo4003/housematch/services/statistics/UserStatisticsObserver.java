package ca.ulaval.glo4003.housematch.services.statistics;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserObserver;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;

public class UserStatisticsObserver implements UserObserver {

    private UserStatisticsService userStatisticsService;

    public UserStatisticsObserver(final UserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    @Override
    public void userStatusChanged(Object sender, UserStatus newStatus) {
        switch (newStatus) {
        case ACTIVE:
            userStatisticsService.processUserStatusChangeToActive((User) sender);
            break;
        case INACTIVE:
            userStatisticsService.processUserStatusChangeToInactive((User) sender);
            break;
        default:
        }
    }

    @Override
    public void userNotificationQueued(Object sender, Notification notification) {
        // Event intentionally ignored.
    }
}
