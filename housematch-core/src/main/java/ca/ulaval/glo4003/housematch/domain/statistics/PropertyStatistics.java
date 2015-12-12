package ca.ulaval.glo4003.housematch.domain.statistics;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

public class PropertyStatistics {

    static final String NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME = "NumberOfSoldPropertiesThisYear";
    static final String NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME = "NumberOfPropertiesForSale";
    private static final Integer DEFAULT_VALUE = 0;

    private Statistic<Integer> numberOfSoldPropertiesThisYearStat;
    private Map<PropertyType, Statistic<Integer>> numberOfPropertiesForSaleStat = new HashMap<>();

    public PropertyStatistics(final StatisticFactory statisticFactory) {
        initStats(statisticFactory);
    }

    private void initStats(final StatisticFactory statisticFactory) {
        numberOfSoldPropertiesThisYearStat = statisticFactory.createStatistic(NUMBER_OF_SOLD_PROPERTIES_THIS_YEAR_STAT_NAME, DEFAULT_VALUE);
        for (PropertyType propertyType : PropertyType.values()) {
            Statistic<Integer> statistic = statisticFactory.createStatistic(getPropertiesForSaleStatName(propertyType), DEFAULT_VALUE);
            numberOfPropertiesForSaleStat.put(propertyType, statistic);
        }
    }

    public Integer getNumberOfSoldPropertiesThisYear() {
        return numberOfSoldPropertiesThisYearStat.getValue();
    }

    public void setNumberOfSoldPropertiesThisYear(Integer numberOfSoldPropertiesThisYear) {
        numberOfSoldPropertiesThisYearStat.setValue(numberOfSoldPropertiesThisYear);
    }

    public Map<PropertyType, Statistic<Integer>> getNumberOfPropertiesForSale() {
        return numberOfPropertiesForSaleStat;
    }

    public Integer getNumberOfPropertiesForSale(PropertyType propertyType) {
        return numberOfPropertiesForSaleStat.get(propertyType).getValue();
    }

    public void setNumberOfPropertiesForSale(PropertyType propertyType, Integer value) {
        numberOfPropertiesForSaleStat.get(propertyType).setValue(value);
    }

    private String getPropertiesForSaleStatName(PropertyType propertyType) {
        return String.format("%s_%s", NUMBER_OF_PROPERTIES_FOR_SALE_STAT_NAME, propertyType.name());
    }
}
