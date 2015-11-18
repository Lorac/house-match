package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.utils.Observable;

public class PropertyObservable extends Observable<PropertyObserver> {

    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        for (PropertyObserver observer : observers) {
            observer.propertyStatusChanged(property, newStatus);
        }
    }
}
