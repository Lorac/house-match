package ca.ulaval.glo4003.housematch.services.statistics;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.statistics.PropertyStatistics;

public class PropertyStatisticsService {

    private PropertyStatistics propertyStatistics;

    public PropertyStatisticsService(final PropertyStatistics propertyStatistics) {
        this.propertyStatistics = propertyStatistics;
    }

    public PropertyStatistics getStatistics() {
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
        propertyStatistics.setNumberOfSoldPropertiesThisYear(propertyStatistics.getNumberOfSoldPropertiesThisYear() + value);
    }

    private synchronized void adjustNumberOfPropertiesForSale(PropertyType propertyType, Integer value) {
        propertyStatistics.setNumberOfPropertiesForSale(propertyType,
                propertyStatistics.getNumberOfPropertiesForSale(propertyType) + value);
    }

}
