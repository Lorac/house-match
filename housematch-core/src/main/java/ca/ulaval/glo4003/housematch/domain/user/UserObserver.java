package ca.ulaval.glo4003.housematch.domain.user;

public interface UserObserver {

    public void userStatusChanged(User user, UserStatus newStatus);

}
