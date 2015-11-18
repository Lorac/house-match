package ca.ulaval.glo4003.housematch.statistics.user;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserObserver;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;

public class UserStatisticsObserver implements UserObserver {

    private UserStatisticsCollector userStatisticsCollector;

    public UserStatisticsObserver(final UserStatisticsCollector userStatisticsCollector) {
        this.userStatisticsCollector = userStatisticsCollector;
    }

    @Override
    public void userStatusChanged(User user, UserStatus newStatus) {
        switch (newStatus) {
        case ACTIVE:
            userStatisticsCollector.applyUserStatusChangeToActive(user);
            break;
        case INACTIVE:
            userStatisticsCollector.applyUserStatusChangeToInactive(user);
            break;
        default:
        }
    }
}