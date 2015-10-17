package ca.ulaval.glo4003.housematch.persistence.user;

import java.util.Collection;
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
    private Map<String, User> users;

    public XmlUserRepository(final XmlRepositoryMarshaller<XmlUserRootElement> xmlRepositoryMarshaller,
            final XmlUserAdapter xmlUserAdapter) {
        this.users = new ConcurrentHashMap<>();
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

    public User getByUsername(String username) throws UserNotFoundException {
        User user = users.get(username);
        if (user == null) {
            throw new UserNotFoundException(String.format("Cannot find user with username '%s'.", username));
        }
        return user;
    }

    public User getByActivationCode(UUID activationCode) throws UserNotFoundException {
        try {
            return users.values().stream().filter(u -> activationCode.equals(u.getActivationCode())).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("Cannot find user with activation code '%s'.", activationCode));
        }
    }

    protected void marshal() {
        xmlUserRootElement.setUsers(users.values());
        xmlRepositoryMarshaller.marshal(xmlUserRootElement);
    }
}
