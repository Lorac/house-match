package ca.ulaval.glo4003.housematch.domain.statistics;

public interface StatisticsRepository {

    Object get(String statisticName, Object defaultValue);

    void persist(String statisticName, Object value);

}
