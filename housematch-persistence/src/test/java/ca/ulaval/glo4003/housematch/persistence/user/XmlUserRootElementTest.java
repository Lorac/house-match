package ca.ulaval.glo4003.housematch.persistence.user;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;

public class XmlUserRootElementTest {

    private XmlUserRootElement xmlUserRootElement;
    private List<User> users = new ArrayList<>();

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
