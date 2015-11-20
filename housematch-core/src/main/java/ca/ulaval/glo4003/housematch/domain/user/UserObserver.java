package ca.ulaval.glo4003.housematch.domain.user;

public interface UserObserver {

    void userStatusChanged(User user, UserStatus newStatus);

}
