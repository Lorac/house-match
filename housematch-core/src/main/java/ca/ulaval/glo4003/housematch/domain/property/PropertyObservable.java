package ca.ulaval.glo4003.housematch.domain.property;

import java.util.ArrayList;
import java.util.List;

public class PropertyObservable {

    private List<PropertyObserver> observers = new ArrayList<>();

    public void registerObserver(PropertyObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(PropertyObserver observer) {
        observers.remove(observer);
    }

    public void propertyStatusChanged(Property property, PropertyStatus newStatus) {
        for (PropertyObserver observer : observers) {
            observer.propertyStatusChanged(property, newStatus);
        }
    }
}
