package ca.ulaval.glo4003.housematch.statistics.property;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.statistics.Statistic;
import ca.ulaval.glo4003.housematch.statistics.StatisticsRepository;

public class PropertyStatisticsCollector {
    private static final String NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME = "NumberOfSoldPropertiesThisYearToDate";
    private static final String NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME = "NumberOfPropertiesForSale";

    private Statistic<Integer> numberOfSoldPropertiesThisYear;
    private Statistic<Map<PropertyType, Integer>> numberOfPropertiesForSale;

    public PropertyStatisticsCollector(StatisticsRepository statisticsRepository) {
        numberOfSoldPropertiesThisYear = new Statistic<>(0, NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME, statisticsRepository);
        numberOfPropertiesForSale = new Statistic<>(new HashMap<>(), NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME, statisticsRepository);
    }

    public void applyPropertySale(Property property) {
        adjustNumberOfSoldProperties(+1);
        adjustNumberOfPropertiesForSale(property.getPropertyType(), -1);
    }

    public void applyPropertyForSale(Property property) {
        adjustNumberOfPropertiesForSale(property.getPropertyType(), +1);
    }

    private synchronized void adjustNumberOfSoldProperties(Integer value) {
        numberOfSoldPropertiesThisYear.setValue(numberOfSoldPropertiesThisYear.getValue() + value);
    }

    private synchronized void adjustNumberOfPropertiesForSale(PropertyType propertyType, Integer value) {
        numberOfPropertiesForSale.getValue().putIfAbsent(propertyType, value);
        numberOfPropertiesForSale.getValue().computeIfPresent(propertyType, (k, v) -> v + value);
    }

    public PropertyStatistics getStatistics() {
        PropertyStatistics propertyStatistics = new PropertyStatistics();
        propertyStatistics.setNumberOfPropertiesForSale(numberOfPropertiesForSale.getValue());
        propertyStatistics.setNumberOfSoldPropertiesThisYear(numberOfSoldPropertiesThisYear.getValue());
        return propertyStatistics;
    }
}
