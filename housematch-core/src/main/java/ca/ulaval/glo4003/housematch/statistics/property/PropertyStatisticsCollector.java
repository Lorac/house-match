package ca.ulaval.glo4003.housematch.statistics.property;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.statistics.Statistic;
import ca.ulaval.glo4003.housematch.statistics.StatisticFactory;

public class PropertyStatisticsCollector {

    static final String NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME = "NumberOfSoldPropertiesThisYear";
    static final String NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME = "NumberOfPropertiesForSale";
    private static final Integer DEFAULT_INT_VALUE = 0;

    private Statistic<Integer> numberOfSoldPropertiesThisYear;
    private Statistic<Map<PropertyType, Integer>> numberOfPropertiesForSale;

    public PropertyStatisticsCollector(final StatisticFactory statisticFactory) {
        numberOfSoldPropertiesThisYear = statisticFactory.createStatistic(NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME, DEFAULT_INT_VALUE);
        numberOfPropertiesForSale = statisticFactory.createStatistic(NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME, new HashMap<>());
    }

    public void processNewPropertyForSale(Property property) {
        adjustNumberOfPropertiesForSale(property.getPropertyType(), 1);
    }

    public void processPropertySale(Property property) {
        adjustNumberOfSoldPropertiesThisYear(1);
        adjustNumberOfPropertiesForSale(property.getPropertyType(), -1);
    }

    private synchronized void adjustNumberOfSoldPropertiesThisYear(Integer value) {
        numberOfSoldPropertiesThisYear.setValue(numberOfSoldPropertiesThisYear.getValue() + value);
    }

    private synchronized void adjustNumberOfPropertiesForSale(PropertyType propertyType, Integer value) {
        numberOfPropertiesForSale.getValue().computeIfPresent(propertyType, (k, v) -> v + value);
        numberOfPropertiesForSale.getValue().putIfAbsent(propertyType, value);
    }

    public PropertyStatistics getStatistics() {
        PropertyStatistics propertyStatistics = new PropertyStatistics();
        propertyStatistics.setNumberOfSoldPropertiesThisYear(numberOfSoldPropertiesThisYear.getValue());
        propertyStatistics.setNumberOfPropertiesForSale(numberOfPropertiesForSale.getValue());
        return propertyStatistics;
    }
}
