package ca.ulaval.glo4003.housematch.persistence.user;

import ca.ulaval.glo4003.housematch.domain.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;

public class XmlUserRootElementTest {

    private XmlUserRootElement xmlUserRootElement;
    private List<User> users = new ArrayList<User>();

    @Before
    public void init() {
        xmlUserRootElement = new XmlUserRootElement();
    }

    @Test
    public void setingTheUsersSetsTheUsers() {
        xmlUserRootElement.setUsers(users);
        assertSame(users, xmlUserRootElement.getUsers());
    }
}
