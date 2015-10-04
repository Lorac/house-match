package ca.ulaval.glo4003.housematch.domain.user;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.XmlUserRootElement;

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
