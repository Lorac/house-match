package ca.ulaval.glo4003.housematch.domain.user;

public interface UserRepository {

    void persist(User user) throws UserAlreadyExistsException;

    User getByUsername(String username) throws UserNotFoundException;

    User getByActivationCode(Integer activationCode) throws UserNotFoundException;

}
