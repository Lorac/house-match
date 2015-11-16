package ca.ulaval.glo4003.housematch.statistics.user;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;

public class UserStatisticsObserver {

    private UserStatisticsCollector userStatisticsCollector;

    public void propertyStatusChanged(User user, UserStatus newStatus) {
        switch (newStatus) {
        case ACTIVE:
            userStatisticsCollector.applyUserStatusChangedToActive(user);
        case INACTIVE:
            userStatisticsCollector.applyUserStatusChangedToInactive(user);
        default:
        }
    }

}
