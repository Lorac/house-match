package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

public interface PropertyObserver {

    void propertyStatusChanged(Object sender, PropertyStatus newStatus);

    void propertyDetailsChanged(Object sender, PropertyDetails newPropertyDetails);

    void propertyPhotoRejected(Object sender, PropertyPhoto propertyPhoto);

}
