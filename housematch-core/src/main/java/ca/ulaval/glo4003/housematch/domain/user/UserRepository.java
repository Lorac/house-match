package ca.ulaval.glo4003.housematch.domain.user;

public interface UserRepository {

    void persist(User user);

    User getByUsername(String username);

    User getByHashCode(int hash);

}
