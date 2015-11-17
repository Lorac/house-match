package ca.ulaval.glo4003.housematch.statistics.user;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserObserver;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;

public class UserStatisticsObserver implements UserObserver {

    private UserStatisticsCollector userStatisticsCollector;

    public UserStatisticsObserver(UserStatisticsCollector userStatisticsCollector) {
        this.userStatisticsCollector = userStatisticsCollector;
    }

    @Override
    public void userStatusChanged(User user, UserStatus newStatus) {
        switch (newStatus) {
        case ACTIVE:
            userStatisticsCollector.applyUserStatusChangedToActive(user);
        case INACTIVE:
            userStatisticsCollector.applyUserStatusChangedToInactive(user);
        default:
        }
    }
}
