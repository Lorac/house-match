package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.utils.Observable;

public class PropertyObservable extends Observable<PropertyObserver> {

    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        observers.stream().forEach(o -> o.propertyStatusChanged(property, newStatus));
    }

    public void propertyDetailsChanged(Property property, PropertyDetails newPropertyDetails) {
        observers.stream().forEach(o -> o.propertyDetailsChanged(property, newPropertyDetails));
    }

    public void propertyPhotoRejected(Property property, PropertyPhoto propertyPhoto) {
        observers.stream().forEach(o -> o.propertyPhotoRejected(property, propertyPhoto));
    }
}
