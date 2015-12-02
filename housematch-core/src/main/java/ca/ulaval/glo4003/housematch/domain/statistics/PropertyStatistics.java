package ca.ulaval.glo4003.housematch.domain.statistics;

import java.util.Map;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

public class PropertyStatistics {

    private Integer numberOfSoldPropertiesThisYear;
    private Map<PropertyType, Integer> numberOfPropertiesForSale;

    public Integer getNumberOfSoldPropertiesThisYear() {
        return numberOfSoldPropertiesThisYear;
    }

    public void setNumberOfSoldPropertiesThisYear(Integer numberOfSoldPropertiesThisYear) {
        this.numberOfSoldPropertiesThisYear = numberOfSoldPropertiesThisYear;
    }

    public Map<PropertyType, Integer> getNumberOfPropertiesForSale() {
        return numberOfPropertiesForSale;
    }

    public void setNumberOfPropertiesForSale(Map<PropertyType, Integer> numberOfPropertiesForSale) {
        this.numberOfPropertiesForSale = numberOfPropertiesForSale;
    }
}
