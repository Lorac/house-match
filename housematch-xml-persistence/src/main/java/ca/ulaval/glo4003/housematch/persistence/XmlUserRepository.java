package ca.ulaval.glo4003.housematch.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.services.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.services.UserNotFoundException;

public class XmlUserRepository implements UserRepository {

    private XmlMarshaller xmlMarshaller;
    private XmlRootNodeAssembler xmlRootNodeAssembler;
    private List<User> users = new ArrayList<User>();

    public XmlUserRepository() {
        xmlMarshaller = XmlMarshaller.getInstance();
        initRepository();
    }

    public XmlUserRepository(final XmlMarshaller xmlMarchaller) {
        this.xmlMarshaller = xmlMarchaller;
        initRepository();
    }

    private void initRepository() {
        xmlRootNodeAssembler = xmlMarshaller.getRootNode();
        users = xmlRootNodeAssembler.getUsers();
    }

    @Override
    public void persist(User newUser) {
        if (users.stream().anyMatch(u -> u.equals(newUser))) {
            throw new UserAlreadyExistsException(
                    String.format("A user with username '%s' already exists.", newUser.getUsername()));
        }

        users.add(newUser);
        marshall();
    }

    @Override
    public User getByUsername(String username) {
        try {
            return users.stream().filter(u -> u.isUsernameEqual(username)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("Cannot find user with username '%s'.", username));
        }
    }

    private void marshall() {
        xmlRootNodeAssembler.setUsers(users);
        xmlMarshaller.marshall();
    }
}
