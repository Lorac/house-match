package ca.ulaval.glo4003.housematch.services.statistics;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;

public class PropertyStatisticsObserver implements PropertyObserver {

    private PropertyStatisticsService propertyStatisticsService;

    public PropertyStatisticsObserver(final PropertyStatisticsService propertyStatisticsService) {
        this.propertyStatisticsService = propertyStatisticsService;
    }

    @Override
    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        switch (newStatus) {
        case FOR_SALE:
            propertyStatisticsService.applyNewPropertyForSale(property);
            break;
        case SOLD:
            propertyStatisticsService.applyPropertySale(property);
            break;
        default:
        }
    }

}
