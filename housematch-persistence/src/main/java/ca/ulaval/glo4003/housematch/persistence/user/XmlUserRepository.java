package ca.ulaval.glo4003.housematch.persistence.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlUserRepository implements UserRepository {

    private final XmlRepositoryMarshaller<XmlUserRootElement> xmlRepositoryMarshaller;
    private XmlUserRootElement xmlUserRootElement;
    private Map<String, User> users = new ConcurrentHashMap<>();

    public XmlUserRepository(final XmlRepositoryMarshaller<XmlUserRootElement> xmlRepositoryMarshaller,
            final XmlUserAdapter xmlUserAdapter) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository(xmlUserAdapter);
    }

    private void initRepository(final XmlUserAdapter xmlUserAdapter) {
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlUserAdapter);
        xmlUserRootElement = xmlRepositoryMarshaller.unmarshal();

        Collection<User> userElements = xmlUserRootElement.getUsers();
        userElements.forEach(u -> users.put(u.getUsername(), u));
    }

    @Override
    public void persist(User user) throws UserAlreadyExistsException {
        if (users.containsValue(user)) {
            throw new UserAlreadyExistsException(String.format("A user with username '%s' already exists.", user.getUsername()));
        }

        users.put(user.getUsername(), user);
        marshal();
    }

    @Override
    public void update(User user) {
        if (!users.containsValue(user)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        }

        marshal();
    }

    @Override
    public User getByUsername(String username) throws UserNotFoundException {
        try {
            return users.values().stream().filter(u -> u.usernameEquals(username)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("Cannot find user with username '%s'.", username));
        }
    }

    @Override
    public User getByActivationCode(UUID activationCode) throws UserNotFoundException {
        try {
            return users.values().stream().filter(u -> activationCode.equals(u.getActivationCode())).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("Cannot find user with activation code '%s'.", activationCode));
        }
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<User>(users.values());
    }

    private void marshal() {
        xmlUserRootElement.setUsers(users.values());
        xmlRepositoryMarshaller.marshal(xmlUserRootElement);
    }
}
