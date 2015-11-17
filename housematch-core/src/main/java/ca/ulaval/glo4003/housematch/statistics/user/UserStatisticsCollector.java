package ca.ulaval.glo4003.housematch.statistics.user;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.statistics.Statistic;
import ca.ulaval.glo4003.housematch.statistics.StatisticsRepository;

public class UserStatisticsCollector {
    private static final String NUMBER_OF_ACTIVE_BUYERS = "NumberOfActiveBuyers";
    private static final String NUMBER_OF_ACTIVE_SELLERS = "NumberOfActiveSellers";

    private StatisticsRepository statisticsRepository;

    private Statistic<Integer> numberOfActiveBuyers = new Statistic<>(0, NUMBER_OF_ACTIVE_BUYERS, statisticsRepository);
    private Statistic<Integer> numberOfActiveSellers = new Statistic<>(0, NUMBER_OF_ACTIVE_SELLERS, statisticsRepository);

    public UserStatisticsCollector(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
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
