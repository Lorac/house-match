package ca.ulaval.glo4003.housematch.statistics;

public interface StatisticsRepository {

    public Object get(String statisticName, Object defaultValue);

    public void persist(String statisticName, Object value);

}
