package ca.ulaval.glo4003.housematch.domain.statistics;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

public class PropertyStatistics {

    private Integer numberOfSoldPropertiesThisYear = 0;
    private Map<PropertyType, Integer> numberOfPropertiesForSale = new HashMap<>();

    public Integer getNumberOfSoldPropertiesThisYear() {
        return numberOfSoldPropertiesThisYear;
    }

    public void setNumberOfSoldPropertiesThisYear(Integer numberOfSoldPropertiesThisYear) {
        this.numberOfSoldPropertiesThisYear = numberOfSoldPropertiesThisYear;
    }

    public Map<PropertyType, Integer> getNumberOfPropertiesForSale() {
        return numberOfPropertiesForSale;
    }

    public void setNumberOfPropertiesForSale(PropertyType propertyType, Integer value) {
        this.numberOfPropertiesForSale.put(propertyType, value);
    }
}
