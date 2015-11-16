package ca.ulaval.glo4003.housematch.domain.user;

import java.util.ArrayList;
import java.util.List;

public class UserObservable {

    private List<UserObserver> observers = new ArrayList<>();

    public void registerObserver(UserObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(UserObserver observer) {
        observers.remove(observer);
    }

    public void userStatusChanged(User user, UserStatus newStatus) {
        for (UserObserver observer : observers) {
            observer.userStatusChanged(user, newStatus);
        }
    }
}
