package ca.ulaval.glo4003.housematch.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;

public class XmlUserRepository implements UserRepository {

    private XmlRepositoryMarshaller xmlRepositoryMarshaller;
    private XmlRootElementWrapper xmlRootElementWrapper;
    private List<User> users = new ArrayList<User>();

    public XmlUserRepository() {
        xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
        initRepository();
    }

    public XmlUserRepository(final XmlRepositoryMarshaller xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository();
    }

    protected void initRepository() {
        xmlRootElementWrapper = xmlRepositoryMarshaller.getRootElementWrapper();
        users = xmlRootElementWrapper.getUsers();
    }

    @Override
    public void persist(User newUser) {
        if (users.stream().anyMatch(u -> u.equals(newUser))) {
            throw new UserAlreadyExistsException(
                    String.format("A user with username '%s' already exists.", newUser.getUsername()));
        }

        users.add(newUser);
        marshal();
    }

    @Override
    public User getByUsername(String username) {
        try {
            return users.stream().filter(u -> u.isUsernameEqual(username)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("Cannot find user with username '%s'.", username));
        }
    }

    protected void marshal() {
        xmlRootElementWrapper.setUsers(users);
        xmlRepositoryMarshaller.marshal();
    }
}
