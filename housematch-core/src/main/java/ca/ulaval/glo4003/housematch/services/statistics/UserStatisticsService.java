package ca.ulaval.glo4003.housematch.services.statistics;

import ca.ulaval.glo4003.housematch.domain.statistics.Statistic;
import ca.ulaval.glo4003.housematch.domain.statistics.StatisticFactory;
import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserStatisticsService {

    static final String NUMBER_OF_ACTIVE_BUYERS_STAT_NAME = "NumberOfActiveBuyers";
    static final String NUMBER_OF_ACTIVE_SELLERS_STAT_NAME = "NumberOfActiveSellers";
    private static final Integer DEFAULT_INT_VALUE = 0;

    private Statistic<Integer> numberOfActiveBuyers;
    private Statistic<Integer> numberOfActiveSellers;

    public UserStatisticsService(final StatisticFactory statisticFactory) {
        numberOfActiveBuyers = statisticFactory.createStatistic(NUMBER_OF_ACTIVE_BUYERS_STAT_NAME, DEFAULT_INT_VALUE);
        numberOfActiveSellers = statisticFactory.createStatistic(NUMBER_OF_ACTIVE_SELLERS_STAT_NAME, DEFAULT_INT_VALUE);
    }

    public void applyUserStatusChangeToActive(User user) {
        if (user.hasRole(UserRole.BUYER)) {
            adjustNumberOfActiveBuyers(1);
        } else if (user.hasRole(UserRole.SELLER)) {
            adjustNumberOfActiveSellers(1);
        }
    }

    public void applyUserStatusChangeToInactive(User user) {
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
        numberOfActiveSellers.setValue(numberOfActiveSellers.getValue() + value);
    }

    public UserStatistics getStatistics() {
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setNumberOfActiveBuyers(numberOfActiveBuyers.getValue());
        userStatistics.setNumberOfActiveSellers(numberOfActiveSellers.getValue());
        return userStatistics;
    }
}
