package ca.ulaval.glo4003.housematch.persistence;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.services.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.services.UserNotFoundException;

public class XmlUserRepository implements UserRepository {

    private XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
    private List<User> users = new ArrayList<User>();

    public XmlUserRepository() {
        XmlRootNodeAssembler xmlRootNode = xmlRepositoryMarshaller.getRootNode();
        users = xmlRootNode.getUsers();
    }

    @Override
    public void persist(User newUser) {
        for (User user : users) {
            if (user.getUsername().compareToIgnoreCase(newUser.getUsername()) == 0) {
                throw new UserAlreadyExistsException(
                        String.format("A user with username '%s' already exists.", user.getUsername()));
            }
        }

        users.add(newUser);
        marshall();
    }

    @Override
    public User getByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().compareTo(username) == 0) {
                return user;
            }
        }

        throw new UserNotFoundException(String.format("Cannot find user with username '%s'.", username));
    }

    private void marshall() {
        XmlRootNodeAssembler rootXmlNode = xmlRepositoryMarshaller.getRootNode();
        rootXmlNode.setUsers(users);
        xmlRepositoryMarshaller.marshall();
    }
}
