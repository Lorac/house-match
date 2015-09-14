package ca.ulaval.glo4003.housematch.persistence;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.services.UserNotFoundException;

public class XmlUserRepositoryTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_UNEXISTING_USERNAME = "username2";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_PASSWORD = "password1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    private XmlRepositoryMarshaller xmlRepositoryMarshallerMock;
    private XmlRootNodeAssembler xmlRootNodeAssemblerMock;
    private XmlUserRepository xmlUserRepository;

    @Before
    public void init() {
        initMocks();
        stubMethods();
        xmlUserRepository = new XmlUserRepository(xmlRepositoryMarshallerMock);
    }

    private void stubMethods() {
        when(xmlRepositoryMarshallerMock.getRootNodeAssembler()).thenReturn(xmlRootNodeAssemblerMock);
        when(xmlRootNodeAssemblerMock.getUsers()).thenReturn(new ArrayList<User>());
    }

    private void initMocks() {
        xmlRepositoryMarshallerMock = mock(XmlRepositoryMarshaller.class);
        xmlRootNodeAssemblerMock = mock(XmlRootNodeAssembler.class);
    }

    @Test
    public void persistMethodPersistsUserToRepository() throws Exception {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        assertSame(user, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void persistingUserWhichAlreadyExistsThrowsUserAlreadyExistsException() {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        User user2 = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        xmlUserRepository.persist(user2);
    }

    @Test
    public void getByUsernameMethodRetrievesUserByUsername() {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        assertSame(user, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }

    @Test(expected = UserNotFoundException.class)
    public void retrievingUserUsingNonExistingUsernameThrowsUserNotFoundException() {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        assertSame(user, xmlUserRepository.getByUsername(SAMPLE_UNEXISTING_USERNAME));
    }
}
