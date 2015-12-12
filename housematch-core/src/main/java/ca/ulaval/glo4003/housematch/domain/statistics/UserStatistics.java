package ca.ulaval.glo4003.housematch.domain.statistics;

public class UserStatistics {

    static final String NUMBER_OF_ACTIVE_BUYERS_STAT_NAME = "NumberOfActiveBuyers";
    static final String NUMBER_OF_ACTIVE_SELLERS_STAT_NAME = "NumberOfActiveSellers";
    private static final Integer DEFAULT_VALUE = 0;

    private Statistic<Integer> numberOfActiveBuyersStat;
    private Statistic<Integer> numberOfActiveSellersStat;

    public UserStatistics(final StatisticFactory statisticFactory) {
        initStats(statisticFactory);
    }

    private void initStats(final StatisticFactory statisticFactory) {
        numberOfActiveBuyersStat = statisticFactory.createStatistic(NUMBER_OF_ACTIVE_BUYERS_STAT_NAME, DEFAULT_VALUE);
        numberOfActiveSellersStat = statisticFactory.createStatistic(NUMBER_OF_ACTIVE_SELLERS_STAT_NAME, DEFAULT_VALUE);
    }

    public Integer getNumberOfActiveBuyers() {
        return numberOfActiveBuyersStat.getValue();
    }

    public void setNumberOfActiveBuyers(Integer numberOfActiveBuyers) {
        numberOfActiveBuyersStat.setValue(numberOfActiveBuyers);
    }

    public Integer getNumberOfActiveSellers() {
        return numberOfActiveSellersStat.getValue();
    }

    public void setNumberOfActiveSellers(Integer numberOfActiveSellers) {
        numberOfActiveSellersStat.setValue(numberOfActiveSellers);
    }
}
