package ca.ulaval.glo4003.housematch.services.statistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;
import ca.ulaval.glo4003.housematch.domain.statistics.Statistic;
import ca.ulaval.glo4003.housematch.domain.statistics.StatisticFactory;

public class PropertyStatisticsService {

    static final String NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME = "NumberOfSoldPropertiesThisYear";
    static final String NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME = "NumberOfPropertiesForSale";
    private static final Integer DEFAULT_VALUE = 0;

    private StatisticFactory statisticFactory;

    private Statistic<Integer> numberOfSoldPropertiesThisYearStat;
    private Map<PropertyType, Statistic<Integer>> numberOfPropertiesForSaleStat = new HashMap<>();

    public PropertyStatisticsService(final StatisticFactory statisticFactory) {
        this.statisticFactory = statisticFactory;
        initStats();
    }

    public void initStats() {
        numberOfSoldPropertiesThisYearStat = statisticFactory.createStatistic(NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME, DEFAULT_VALUE);
        for (PropertyType propertyType : PropertyType.values()) {
            Statistic<Integer> statistic = statisticFactory.createStatistic(getPropertyTypeStatName(propertyType), DEFAULT_VALUE);
            numberOfPropertiesForSaleStat.put(propertyType, statistic);
        }
    }

    public PropertyStatistics getStatistics() {
        PropertyStatistics propertyStatistics = new PropertyStatistics();
        propertyStatistics.setNumberOfSoldPropertiesThisYear(numberOfSoldPropertiesThisYearStat.getValue());
        for (Entry<PropertyType, Statistic<Integer>> entry : numberOfPropertiesForSaleStat.entrySet()) {
            propertyStatistics.setNumberOfPropertiesForSale(entry.getKey(), entry.getValue().getValue());
        }
        return propertyStatistics;
    }

    public void processNewPropertyForSale(Property property) {
        adjustNumberOfPropertiesForSale(property.getPropertyType(), 1);
    }

    public void processPropertySale(Property property) {
        adjustNumberOfSoldPropertiesThisYear(1);
        adjustNumberOfPropertiesForSale(property.getPropertyType(), -1);
    }

    private synchronized void adjustNumberOfSoldPropertiesThisYear(Integer value) {
        numberOfSoldPropertiesThisYearStat.setValue(numberOfSoldPropertiesThisYearStat.getValue() + value);
    }

    private synchronized void adjustNumberOfPropertiesForSale(PropertyType propertyType, Integer value) {
        numberOfPropertiesForSaleStat.get(propertyType).setValue(numberOfPropertiesForSaleStat.get(propertyType).getValue() + value);
    }

    private String getPropertyTypeStatName(PropertyType propertyType) {
        return String.format("%s_%s", NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME, propertyType.name());
    }

}
