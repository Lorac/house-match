package ca.ulaval.glo4003.housematch.statistics.user;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.statistics.Statistic;
import ca.ulaval.glo4003.housematch.statistics.StatisticsRepository;

public class UserStatisticsCollector {
    private static final String NUMBER_OF_ACTIVE_BUYERS_STAT_NAME = "NumberOfActiveBuyers";
    private static final String NUMBER_OF_ACTIVE_SELLERS_STAT_NAME = "NumberOfActiveSellers";

    private Statistic<Integer> numberOfActiveBuyers;
    private Statistic<Integer> numberOfActiveSellers;

    public UserStatisticsCollector(StatisticsRepository statisticsRepository) {
        numberOfActiveBuyers = new Statistic<>(0, NUMBER_OF_ACTIVE_BUYERS_STAT_NAME, statisticsRepository);
        numberOfActiveSellers = new Statistic<>(0, NUMBER_OF_ACTIVE_SELLERS_STAT_NAME, statisticsRepository);
    }

    public void applyUserStatusChangedToActive(User user) {
        if (user.hasRole(UserRole.BUYER)) {
            adjustNumberOfActiveBuyers(+1);
        } else if (user.hasRole(UserRole.SELLER)) {
            adjustNumberOfActiveSellers(+1);
        }
    }

    public void applyUserStatusChangedToInactive(User user) {
        if (user.hasRole(UserRole.BUYER)) {
            adjustNumberOfActiveBuyers(-1);
        } else if (user.hasRole(UserRole.SELLER)) {
            adjustNumberOfActiveSellers(-1);
        }
    }

    private synchronized void adjustNumberOfActiveBuyers(Integer value) {
        numberOfActiveBuyers.setValue(numberOfActiveBuyers.getValue() + value);
    }

    private synchronized void adjustNumberOfActiveSellers(Integer value) {
        numberOfActiveBuyers.setValue(numberOfActiveBuyers.getValue() + value);
    }

    public UserStatistics getStatistics() {
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setNumberOfActiveBuyers(numberOfActiveBuyers.getValue());
        userStatistics.setNumberOfActiveSellers(numberOfActiveSellers.getValue());
        return userStatistics;
    }
}
