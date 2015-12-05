package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import ca.ulaval.glo4003.housematch.utils.Observable;

public class PropertyPhotoObservable extends Observable<PropertyPhotoObserver> {

    public void propertyPhotoStatusChanged(PropertyPhoto propertyPhoto, PropertyPhotoStatus newStatus) {
        observers.stream().forEach(o -> o.propertyPhotoStatusChanged(propertyPhoto, newStatus));
    }

}
