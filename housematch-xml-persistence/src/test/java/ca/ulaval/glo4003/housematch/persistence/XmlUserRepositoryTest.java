package ca.ulaval.glo4003.housematch.persistence;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class XmlUserRepositoryTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_PASSWORD = "password1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    private XmlMarshaller xmlMarshallerMock;
    private XmlRootNodeAssembler xmlRootNodeAssemblerMock;
    private XmlUserRepository xmlUserRepository;

    @Before
    public void init() throws Exception {
        initMocks();
        stubMethods();
        xmlUserRepository = new XmlUserRepository(xmlMarshallerMock);
    }

    private void stubMethods() {
        when(xmlMarshallerMock.getRootNode()).thenReturn(xmlRootNodeAssemblerMock);
        when(xmlRootNodeAssemblerMock.getUsers()).thenReturn(new ArrayList<User>());
    }

    private void initMocks() {
        xmlMarshallerMock = mock(XmlMarshaller.class);
        xmlRootNodeAssemblerMock = mock(XmlRootNodeAssembler.class);
    }

    @Test
    public void userIsAddedToRepositoryOnPersist() throws Exception {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        assertSame(user, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }

    @Test
    public void repositoryRetrievesUserByUsername() {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        assertSame(user, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }
}
