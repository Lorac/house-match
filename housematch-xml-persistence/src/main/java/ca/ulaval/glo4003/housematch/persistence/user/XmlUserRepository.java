package ca.ulaval.glo4003.housematch.persistence.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.XmlUserAdapter;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlUserRepository implements UserRepository {

    private final XmlRepositoryMarshaller<XmlUserRootElement> xmlRepositoryMarshaller;
    private XmlUserRootElement xmlUserRootElement;
    private List<User> users;

    public XmlUserRepository(final XmlRepositoryMarshaller<XmlUserRootElement> xmlRepositoryMarshaller,
            final XmlUserAdapter xmlUserAdapter) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository(xmlUserAdapter);
    }

    protected void initRepository(final XmlUserAdapter xmlUserAdapter) {
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlUserAdapter);
        xmlUserRootElement = xmlRepositoryMarshaller.unmarshal();
        users = xmlUserRootElement.getUsers();
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
        xmlUserRootElement.setUsers(users);
        xmlRepositoryMarshaller.marshal(xmlUserRootElement);
    }
}
