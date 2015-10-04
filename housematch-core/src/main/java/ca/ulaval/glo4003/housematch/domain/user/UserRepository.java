package ca.ulaval.glo4003.housematch.domain.user;

import java.util.UUID;

public interface UserRepository {

    void persist(User user) throws UserAlreadyExistsException;

    User getByUsername(String username) throws UserNotFoundException;

    User getByActivationCode(UUID activationCode) throws UserNotFoundException;

}
