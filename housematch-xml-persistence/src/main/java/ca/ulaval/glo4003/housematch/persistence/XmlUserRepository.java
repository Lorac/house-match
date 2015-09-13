package ca.ulaval.glo4003.housematch.persistence;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.services.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.services.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class XmlUserRepository implements UserRepository {

    private XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
    private List<User> users = new ArrayList<>();

    public XmlUserRepository() {
        XmlRootNodeAssembler xmlRootNode = xmlRepositoryMarshaller.getRootNode();
        users = xmlRootNode.getUsers();
    }

    @Override
    public void persist(User newUser) {

        if (users.stream().anyMatch(u -> u.compareTo(newUser))) {
            throw new UserAlreadyExistsException(
                    String.format("A user with username '%s' already exists.", newUser.getUsername()));
        }

        users.add(newUser);
        marshall();
    }

    @Override
    public User getByUsername(String username) {
        try {
            return users.stream().filter(u -> u.compareToUsername(username)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException(String.format("Cannot find user with username '%s'.", username));
        }

    }

    private void marshall() {
        XmlRootNodeAssembler rootXmlNode = xmlRepositoryMarshaller.getRootNode();
        rootXmlNode.setUsers(users);
        xmlRepositoryMarshaller.marshall();
    }
}
