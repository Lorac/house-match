package ca.ulaval.glo4003.housematch.statistics;

public class Statistic<T> {

    protected String statisticName;
    protected T value;
    protected StatisticsRepository statisticRepository;

    @SuppressWarnings("unchecked")
    public Statistic(T initialValue, String statisticName, StatisticsRepository statisticRepository) {
        this.statisticName = statisticName;
        this.statisticRepository = statisticRepository;
        value = (T) statisticRepository.get(statisticName, initialValue);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        statisticRepository.persist(statisticName, value);
    }
}
