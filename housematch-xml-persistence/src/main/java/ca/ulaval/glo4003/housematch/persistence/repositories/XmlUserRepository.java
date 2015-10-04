package ca.ulaval.glo4003.housematch.persistence.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlUserRepository implements UserRepository {

    private final XmlRepositoryMarshaller xmlRepositoryMarshaller;
    private List<User> users = new ArrayList<>();

    public XmlUserRepository(final XmlRepositoryMarshaller xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository();
    }

    protected void initRepository() {
        users = xmlRepositoryMarshaller.getUsers();
    }

    @Override
    public void persist(User newUser) throws UserAlreadyExistsException {
        if (users.stream().anyMatch(u -> u.equals(newUser))) {
            throw new UserAlreadyExistsException(
                    String.format("A user with username '%s' already exists.", newUser.getUsername()));
        }

        users.add(newUser);
        marshal();
    }

    public User getByUsername(String username) throws UserNotFoundException {
        try {
            return users.stream().filter(u -> u.usernameEquals(username)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("Cannot find user with username '%s'.", username));
        }
    }

    public User getByActivationCode(UUID activationCode) throws UserNotFoundException {
        try {
            return users.stream().filter(u -> activationCode.equals(u.getActivationCode())).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(
                    String.format("Cannot find user with activation code '%s'.", activationCode));
        }
    }

    protected void marshal() {
        xmlRepositoryMarshaller.setUsers(users);
    }
}
