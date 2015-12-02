package ca.ulaval.glo4003.housematch.services.statistics;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserObserver;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;

public class UserStatisticsObserver implements UserObserver {

    private UserStatisticsService userStatisticsService;

    public UserStatisticsObserver(final UserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    @Override
    public void userStatusChanged(User user, UserStatus newStatus) {
        switch (newStatus) {
        case ACTIVE:
            userStatisticsService.applyUserStatusChangeToActive(user);
            break;
        case INACTIVE:
            userStatisticsService.applyUserStatusChangeToInactive(user);
            break;
        default:
        }
    }
}
