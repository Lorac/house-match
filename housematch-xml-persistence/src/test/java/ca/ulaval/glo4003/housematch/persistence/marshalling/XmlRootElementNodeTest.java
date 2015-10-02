package ca.ulaval.glo4003.housematch.persistence.marshalling;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRootElementNode;

public class XmlRootElementNodeTest {

    private XmlRootElementNode xmlRootElementNode;
    private static final List<User> SAMPLE_USER_LIST = new ArrayList<User>();

    @Before
    public void init() throws Exception {
        xmlRootElementNode = new XmlRootElementNode();
    }

    @Test
    public void settingTheUsersSetsTheSpecifiedListOfUsers() {
        xmlRootElementNode.setUsers(SAMPLE_USER_LIST);
        assertEquals(SAMPLE_USER_LIST, xmlRootElementNode.getUsers());
    }
}
