package ca.ulaval.glo4003.housematch.statistics.user;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.statistics.StatisticsRepository;

public class UserStatisticsCollector {

    static final String NUMBER_OF_ACTIVE_BUYERS_STAT_NAME = "NumberOfActiveBuyers";
    static final String NUMBER_OF_ACTIVE_SELLERS_STAT_NAME = "NumberOfActiveSellers";
    private static final Integer DEFAULT_VALUE = 0;

    private StatisticsRepository statisticsRepository;

    private Integer numberOfActiveBuyers;
    private Integer numberOfActiveSellers;

    public UserStatisticsCollector(final StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
        numberOfActiveBuyers = statisticsRepository.get(NUMBER_OF_ACTIVE_BUYERS_STAT_NAME, DEFAULT_VALUE);
        numberOfActiveSellers = statisticsRepository.get(NUMBER_OF_ACTIVE_SELLERS_STAT_NAME, DEFAULT_VALUE);
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
        numberOfActiveBuyers += value;
        statisticsRepository.persist(NUMBER_OF_ACTIVE_BUYERS_STAT_NAME, numberOfActiveBuyers);
    }

    private synchronized void adjustNumberOfActiveSellers(Integer value) {
        numberOfActiveSellers += value;
        statisticsRepository.persist(NUMBER_OF_ACTIVE_SELLERS_STAT_NAME, numberOfActiveSellers);
    }

    public UserStatistics getStatistics() {
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setNumberOfActiveBuyers(numberOfActiveBuyers);
        userStatistics.setNumberOfActiveSellers(numberOfActiveSellers);
        return userStatistics;
    }
}
