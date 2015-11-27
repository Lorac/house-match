package ca.ulaval.glo4003.housematch.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<ObserverType> {

    protected List<ObserverType> observers = new ArrayList<>();

    public void registerObserver(ObserverType observer) {
        observers.add(observer);
    }

    public void unregisterObserver(ObserverType observer) {
        observers.remove(observer);
    }

    public Boolean isObserverRegistered(ObserverType observer) {
        return observers.contains(observer);
    }
}