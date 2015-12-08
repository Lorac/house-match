package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.utils.Observable;

public class PropertyObservable extends Observable<PropertyObserver> {

    public void propertyStatusChanged(Object sender, PropertyStatus newStatus) {
        observers.stream().forEach(o -> o.propertyStatusChanged(sender, newStatus));
    }

    public void propertyDetailsChanged(Object sender, PropertyDetails newPropertyDetails) {
        observers.stream().forEach(o -> o.propertyDetailsChanged(sender, newPropertyDetails));
    }

    public void propertyPhotoRejected(Object sender, PropertyPhoto propertyPhoto) {
        observers.stream().forEach(o -> o.propertyPhotoRejected(sender, propertyPhoto));
    }
}
