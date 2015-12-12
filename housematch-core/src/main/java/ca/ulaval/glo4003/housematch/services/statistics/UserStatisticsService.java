package ca.ulaval.glo4003.housematch.services.statistics;

import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserStatisticsService {

    private UserStatistics userStatistics;

    public UserStatisticsService(final UserStatistics userStatistics) {
        this.userStatistics = userStatistics;
    }

    public UserStatistics getStatistics() {
        return userStatistics;
    }

    public void processUserStatusChangeToActive(User user) {
        if (user.hasRole(UserRole.BUYER)) {
            adjustNumberOfActiveBuyers(1);
        } else if (user.hasRole(UserRole.SELLER)) {
            adjustNumberOfActiveSellers(1);
        }
    }

    public void processUserStatusChangeToInactive(User user) {
        if (user.hasRole(UserRole.BUYER)) {
            adjustNumberOfActiveBuyers(-1);
        } else if (user.hasRole(UserRole.SELLER)) {
            adjustNumberOfActiveSellers(-1);
        }
    }

    private synchronized void adjustNumberOfActiveBuyers(Integer value) {
        userStatistics.setNumberOfActiveBuyers(userStatistics.getNumberOfActiveBuyers() + value);
    }

    private synchronized void adjustNumberOfActiveSellers(Integer value) {
        userStatistics.setNumberOfActiveSellers(userStatistics.getNumberOfActiveSellers() + value);
    }
}
