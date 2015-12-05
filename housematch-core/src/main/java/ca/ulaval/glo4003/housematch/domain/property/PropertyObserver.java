package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

public interface PropertyObserver {

    void propertyStatusChanged(Property property, PropertyStatus newStatus);

    void propertyDetailsChanged(Property property, PropertyDetails newPropertyDetails);

    void propertyPhotoRejected(Property property, PropertyPhoto propertyPhoto);

}
