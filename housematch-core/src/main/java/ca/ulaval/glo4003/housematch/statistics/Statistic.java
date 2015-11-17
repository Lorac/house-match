package ca.ulaval.glo4003.housematch.statistics;

public class Statistic<T> {

    protected String statisticName;
    protected T value;
    protected StatisticsRepository statisticsRepository;

    @SuppressWarnings("unchecked")
    public Statistic(final T initialValue, final String statisticName, final StatisticsRepository statisticsRepository) {
        this.statisticName = statisticName;
        this.statisticsRepository = statisticsRepository;
        value = (T) statisticsRepository.get(statisticName, initialValue);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        statisticsRepository.persist(statisticName, value);
    }
}
