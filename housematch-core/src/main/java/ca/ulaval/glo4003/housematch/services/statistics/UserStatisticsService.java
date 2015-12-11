package ca.ulaval.glo4003.housematch.services.statistics;

import ca.ulaval.glo4003.housematch.domain.statistics.Statistic;
import ca.ulaval.glo4003.housematch.domain.statistics.StatisticFactory;
import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserStatisticsService {

    static final String NUMBER_OF_ACTIVE_BUYERS_STAT_NAME = "NumberOfActiveBuyers";
    static final String NUMBER_OF_ACTIVE_SELLERS_STAT_NAME = "NumberOfActiveSellers";
    private static final Integer DEFAULT_VALUE = 0;

    private Statistic<Integer> numberOfActiveBuyersStat;
    private Statistic<Integer> numberOfActiveSellersStat;

    public UserStatisticsService(final StatisticFactory statisticFactory) {
        numberOfActiveBuyersStat = statisticFactory.createStatistic(NUMBER_OF_ACTIVE_BUYERS_STAT_NAME, DEFAULT_VALUE);
        numberOfActiveSellersStat = statisticFactory.createStatistic(NUMBER_OF_ACTIVE_SELLERS_STAT_NAME, DEFAULT_VALUE);
    }

    public UserStatistics getStatistics() {
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setNumberOfActiveBuyers(numberOfActiveBuyersStat.getValue());
        userStatistics.setNumberOfActiveSellers(numberOfActiveSellersStat.getValue());
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
        numberOfActiveBuyersStat.setValue(numberOfActiveBuyersStat.getValue() + value);
    }

    private synchronized void adjustNumberOfActiveSellers(Integer value) {
        numberOfActiveSellersStat.setValue(numberOfActiveSellersStat.getValue() + value);
    }
}
