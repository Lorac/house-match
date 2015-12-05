package ca.ulaval.glo4003.housematch.services.statistics;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyObserver;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

public class PropertyStatisticsObserver implements PropertyObserver {

    private PropertyStatisticsService propertyStatisticsService;

    public PropertyStatisticsObserver(final PropertyStatisticsService propertyStatisticsService) {
        this.propertyStatisticsService = propertyStatisticsService;
    }

    @Override
    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        switch (newStatus) {
        case FOR_SALE:
            propertyStatisticsService.processNewPropertyForSale(property);
            break;
        case SOLD:
            propertyStatisticsService.processPropertySale(property);
            break;
        default:
        }
    }

    @Override
    public void propertyDetailsChanged(Property property, PropertyDetails newPropertyDetails) {
        // Event intentionally ignored.
    }

    @Override
    public void propertyPhotoRejected(Property property, PropertyPhoto propertyPhoto) {
        // Event intentionally ignored.
    }

}
