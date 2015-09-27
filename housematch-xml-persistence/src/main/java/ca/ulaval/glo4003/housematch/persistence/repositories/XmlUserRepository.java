package ca.ulaval.glo4003.housematch.persistence.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.persistence.XmlRepositoryAssembler;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlUserRepository implements UserRepository {

    private final XmlRepositoryMarshaller xmlRepositoryMarshaller;
    private XmlRepositoryAssembler xmlRepositoryAssembler;
    private List<User> users = new ArrayList<>();

    public XmlUserRepository(final XmlRepositoryMarshaller xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository();
    }

    protected void initRepository() {
        xmlRepositoryAssembler = xmlRepositoryMarshaller.getRepositoryAssembler();
        users = xmlRepositoryAssembler.getUsers();
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

    public User getByHashCode(int hash) throws UserNotFoundException {
        try {
            return users.stream().filter(u -> u.hashCode() == hash).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("Cannot find user with hash '%s'.", hash));
        }
    }

    protected void marshal() {
        xmlRepositoryAssembler.setUsers(users);
        xmlRepositoryMarshaller.marshal();
    }
}
