package ca.ulaval.glo4003.housematch.domain.user;

public class UserFactory {

    public User getUser(final String username, final String email, final String password, final UserRole role) {
        return new User(username, email, password, role);
    }

}
