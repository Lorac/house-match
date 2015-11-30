package ca.ulaval.glo4003.housematch.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Observable<ObserverType> {

    protected Set<ObserverType> observers = new HashSet<>();

    public void registerObserver(ObserverType observer) {
        observers.add(observer);
    }

    public void registerObservers(List<ObserverType> observers) {
        this.observers.addAll(observers);
    }

    public void unregisterObserver(ObserverType observer) {
        observers.remove(observer);
    }

    public Boolean isObserverRegistered(ObserverType observer) {
        return observers.contains(observer);
    }
}
