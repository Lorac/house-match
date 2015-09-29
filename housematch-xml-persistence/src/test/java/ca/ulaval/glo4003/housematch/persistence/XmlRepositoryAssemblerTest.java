package ca.ulaval.glo4003.housematch.persistence;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;

public class XmlRepositoryAssemblerTest {

    private XmlRepositoryAssembler xmlRepositoryAssembler;
    private static final List<User> SAMPLE_USER_LIST = new ArrayList<User>();

    @Before
    public void init() throws Exception {
        xmlRepositoryAssembler = new XmlRepositoryAssembler();
    }

    @Test
    public void settingTheUsersSetsTheSpecifiedListOfUsers() {
        xmlRepositoryAssembler.setUsers(SAMPLE_USER_LIST);
        assertEquals(SAMPLE_USER_LIST, xmlRepositoryAssembler.getUsers());
    }
}
