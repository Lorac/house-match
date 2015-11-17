package ca.ulaval.glo4003.housematch.statistics.property;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class PropertyStatisticsObserver implements PropertyObserver {

    private PropertyStatisticsCollector propertyStatisticsCollector;

    public PropertyStatisticsObserver(PropertyStatisticsCollector propertyStatisticsCollector) {
        this.propertyStatisticsCollector = propertyStatisticsCollector;
    }

    @Override
    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        switch (newStatus) {
        case FOR_SALE:
            propertyStatisticsCollector.applyPropertyForSale(property);
            break;
        case SOLD:
            propertyStatisticsCollector.applyPropertySale(property);
            break;
        default:
        }
    }

}
