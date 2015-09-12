package ca.ulaval.glo4003.housematch.persistence;

import java.util.ArrayList;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.services.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.services.UserNotFoundException;

public class InMemoryUserRepository implements UserRepository {

    private ArrayList<User> users = new ArrayList<User>();

    @Override
    public void persist(User newUser) {
        for (User user : users) {
            if (user.getUsername().compareToIgnoreCase(user.getUsername()) == 0) {
                throw new UserAlreadyExistsException(
                        String.format("A user with username '%s' already exists.", user.getUsername()));
            }
        }

        users.add(newUser);
    }

    @Override
    public User getByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().compareTo(user.getUsername()) == 0) {
                return user;
            }
        }

        throw new UserNotFoundException(String.format("Cannot find user with username '%s'.", username));
    }

}
