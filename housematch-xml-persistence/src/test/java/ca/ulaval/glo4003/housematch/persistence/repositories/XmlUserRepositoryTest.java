package ca.ulaval.glo4003.housematch.persistence.repositories;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlUserRepositoryTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_UNEXISTING_USERNAME = "username2";

    private XmlRepositoryMarshaller xmlRepositoryMarshallerMock;
    private XmlUserRepository xmlUserRepository;
    private User userMock;
    private List<User> users;

    @Before
    public void init() {
        users = new ArrayList<User>();
        initMocks();
        stubMethods();
        xmlUserRepository = new XmlUserRepository(xmlRepositoryMarshallerMock);
    }

    private void stubMethods() {
        when(xmlRepositoryMarshallerMock.getUsers()).thenReturn(users);
        when(userMock.usernameEquals(SAMPLE_USERNAME)).thenReturn(true);
    }

    private void initMocks() {
        userMock = mock(User.class);
        xmlRepositoryMarshallerMock = mock(XmlRepositoryMarshaller.class);
    }

    @Test
    public void persistingUserPersistsUserToRepository() throws Exception {
        xmlUserRepository.persist(userMock);
        assertSame(userMock, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }

    @Test
    public void persistingUserSetsTheUsersInTheRepositoryMarshaller() throws Exception {
        xmlUserRepository.persist(userMock);
        verify(xmlRepositoryMarshallerMock).setUsers(users);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void persistingUserWhichAlreadyExistsThrowsUserAlreadyExistsException() throws Exception {
        xmlUserRepository.persist(userMock);
        xmlUserRepository.persist(userMock);
    }

    @Test
    public void gettingUserByUsernameRetrievesTheUserByUsername() throws Exception {
        xmlUserRepository.persist(userMock);
        assertSame(userMock, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }

    @Test(expected = UserNotFoundException.class)
    public void gettingUserByUsernameUsingNonExistingUsernameThrowsUserNotFoundException() throws Exception {
        xmlUserRepository.persist(userMock);
        assertSame(userMock, xmlUserRepository.getByUsername(SAMPLE_UNEXISTING_USERNAME));
    }

    @Test
    public void gettingUserByHashCodeRetrievesUserByHashCode() throws Exception {
        xmlUserRepository.persist(userMock);
        assertSame(userMock, xmlUserRepository.getByHashCode(userMock.hashCode()));
    }

    @Test(expected = UserNotFoundException.class)
    public void gettingUserByHashCodeUsingNonExistingHashCodeThrowsUserNotFoundException() throws Exception {
        xmlUserRepository.getByHashCode(SAMPLE_UNEXISTING_USERNAME.hashCode());
    }
}
