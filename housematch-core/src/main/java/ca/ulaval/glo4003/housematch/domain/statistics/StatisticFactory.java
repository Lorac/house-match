package ca.ulaval.glo4003.housematch.domain.statistics;

public class StatisticFactory {

    private StatisticsRepository statisticsRepository;

    public StatisticFactory(final StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public <T> Statistic<T> createStatistic(String statisticName, T initialValue) {
        return new Statistic<T>(statisticName, initialValue, statisticsRepository);
    }

}
