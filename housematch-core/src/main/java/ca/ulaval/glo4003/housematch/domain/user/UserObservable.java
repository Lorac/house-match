package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.utils.Observable;

public class UserObservable extends Observable<UserObserver> {

    public void userStatusChanged(User user, UserStatus newStatus) {
        for (UserObserver observer : observers) {
            observer.userStatusChanged(user, newStatus);
        }
    }
}
