package ca.ulaval.glo4003.housematch.statistics;

public interface StatisticsRepository {

    Integer get(String statisticName, Integer defaultValue);

    void persist(String statisticName, Integer value);

}
