package ca.ulaval.glo4003.housematch.statistics.property;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.statistics.StatisticsRepository;

public class PropertyStatisticsCollector {

    static final String NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME = "NumberOfSoldPropertiesThisYearToDate";
    static final String NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME = "NumberOfPropertiesForSale";
    private static final Integer DEFAULT_VALUE = 0;

    private StatisticsRepository statisticsRepository;

    private Integer numberOfSoldPropertiesThisYear;
    private Map<PropertyType, Integer> numberOfPropertiesForSale = new HashMap<>();

    @SuppressWarnings("unchecked")
    public PropertyStatisticsCollector(final StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
        numberOfSoldPropertiesThisYear = (Integer) statisticsRepository.get(NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME, DEFAULT_VALUE);
        numberOfPropertiesForSale = (Map<PropertyType, Integer>) statisticsRepository.get(NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME,
                new HashMap<>());
    }

    public void applyPropertySale(Property property) {
        adjustNumberOfSoldPropertiesThisYear(1);
        adjustNumberOfPropertiesForSale(property.getPropertyType(), -1);
    }

    public void applyPropertyForSale(Property property) {
        adjustNumberOfPropertiesForSale(property.getPropertyType(), 1);
    }

    private synchronized void adjustNumberOfSoldPropertiesThisYear(Integer value) {
        numberOfSoldPropertiesThisYear += value;
        statisticsRepository.persist(NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME, numberOfSoldPropertiesThisYear);
    }

    private synchronized void adjustNumberOfPropertiesForSale(PropertyType propertyType, Integer value) {
        numberOfPropertiesForSale.computeIfPresent(propertyType, (k, v) -> v + value);
        numberOfPropertiesForSale.putIfAbsent(propertyType, value);

        statisticsRepository.persist(NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME, numberOfPropertiesForSale);
    }

    public PropertyStatistics getStatistics() {
        PropertyStatistics propertyStatistics = new PropertyStatistics();
        propertyStatistics.setNumberOfSoldPropertiesThisYear(numberOfSoldPropertiesThisYear);
        propertyStatistics.setNumberOfPropertiesForSale(numberOfPropertiesForSale);
        return propertyStatistics;
    }
}
