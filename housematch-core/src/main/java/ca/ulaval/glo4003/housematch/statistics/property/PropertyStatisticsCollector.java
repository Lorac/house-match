package ca.ulaval.glo4003.housematch.statistics.property;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.statistics.StatisticsRepository;

public class PropertyStatisticsCollector {

    static final String NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME = "NumberOfSoldPropertiesThisYearToDate";
    static final String NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME = "NumberOfPropertiesForSale";
    private static final Integer DEFAULT_VALUE = 0;

    private StatisticsRepository statisticsRepository;

    private Integer numberOfSoldPropertiesThisYear;
    private Map<PropertyType, Integer> numberOfPropertiesForSale = new ConcurrentHashMap<>();

    public PropertyStatisticsCollector(final StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
        initStatistics();
    }

    private void initStatistics() {
        numberOfSoldPropertiesThisYear = statisticsRepository.get(NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME, DEFAULT_VALUE);
        initNumberOfPropertiesForSaleStatistics();
    }

    private void initNumberOfPropertiesForSaleStatistics() {
        for (PropertyType propertyType : PropertyType.values()) {
            Integer value = statisticsRepository.get(getNumberOfPropertiesForSaleStatName(propertyType), DEFAULT_VALUE);
            numberOfPropertiesForSale.put(propertyType, value);
        }
    }

    public void applyNewPropertyForSale(Property property) {
        adjustNumberOfPropertiesForSale(property.getPropertyType(), 1);
    }

    public void applyPropertySale(Property property) {
        adjustNumberOfSoldPropertiesThisYear(1);
        adjustNumberOfPropertiesForSale(property.getPropertyType(), -1);
    }

    private synchronized void adjustNumberOfSoldPropertiesThisYear(Integer value) {
        numberOfSoldPropertiesThisYear += value;
        statisticsRepository.persist(NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME, numberOfSoldPropertiesThisYear);
    }

    private void adjustNumberOfPropertiesForSale(PropertyType propertyType, Integer value) {
        numberOfPropertiesForSale.compute(propertyType, (k, v) -> v + value);
        statisticsRepository.persist(getNumberOfPropertiesForSaleStatName(propertyType), numberOfPropertiesForSale.get(propertyType));
    }

    private String getNumberOfPropertiesForSaleStatName(PropertyType propertyType) {
        return String.format("%s_%s", NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME, propertyType.name());
    }

    public PropertyStatistics getStatistics() {
        PropertyStatistics propertyStatistics = new PropertyStatistics();
        propertyStatistics.setNumberOfSoldPropertiesThisYear(numberOfSoldPropertiesThisYear);
        propertyStatistics.setNumberOfPropertiesForSale(numberOfPropertiesForSale);
        return propertyStatistics;
    }
}
