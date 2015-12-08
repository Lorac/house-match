package ca.ulaval.glo4003.housematch.domain.propertyphoto;

import ca.ulaval.glo4003.housematch.utils.Observable;

public class PropertyPhotoObservable extends Observable<PropertyPhotoObserver> {

    public void propertyPhotoStatusChanged(Object sender, PropertyPhotoStatus newStatus) {
        observers.stream().forEach(o -> o.propertyPhotoStatusChanged(sender, newStatus));
    }

}
