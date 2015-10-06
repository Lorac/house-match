package ca.ulaval.glo4003.housematch.persistence.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlUserRepositoryTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_UNEXISTING_USERNAME = "username2";
    private static final UUID SAMPLE_ACTIVATION_CODE = UUID.randomUUID();
    private static final UUID ANOTHER_SAMPLE_ACTIVATION_CODE = UUID.randomUUID();

    private XmlRepositoryMarshaller<XmlUserRootElement> xmlRepositoryMarshallerMock;
    private XmlUserAdapter xmlUserAdapterMock;
    private XmlUserRootElement xmlUserRootElementMock;
    private User userMock;
    private User anotherUserMock;

    private XmlUserRepository xmlUserRepository;
    private List<User> users;

    @Before
    public void init() {
        users = new ArrayList<User>();
        initMocks();
        stubMethods();
        xmlUserRepository = new XmlUserRepository(xmlRepositoryMarshallerMock, xmlUserAdapterMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        userMock = mock(User.class);
        anotherUserMock = mock(User.class);
        xmlUserAdapterMock = mock(XmlUserAdapter.class);
        xmlUserRootElementMock = mock(XmlUserRootElement.class);
        xmlRepositoryMarshallerMock = mock(XmlRepositoryMarshaller.class);
    }

    private void stubMethods() {
        when(xmlRepositoryMarshallerMock.unmarshal()).thenReturn(xmlUserRootElementMock);
        when(xmlUserRootElementMock.getUsers()).thenReturn(users);
    }

    @Test
    public void persistingUserPersistsUserToRepository() throws Exception {
        xmlUserRepository.persist(userMock);
        assertThat(users, contains(userMock));
    }

    @Test
    public void persistingUserMarshallsTheUsersInTheRepositoryMarshaller() throws Exception {
        xmlUserRepository.persist(userMock);

        verify(xmlUserRootElementMock).setUsers(users);
        verify(xmlRepositoryMarshallerMock).marshal(xmlUserRootElementMock);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void persistingUserWhichAlreadyExistsThrowsUserAlreadyExistsException() throws Exception {
        xmlUserRepository.persist(userMock);
        xmlUserRepository.persist(userMock);
    }

    @Test
    public void updatingUserUpdatesUserToRepository() throws Exception {
        xmlUserRepository.persist(userMock);
        xmlUserRepository.update(userMock);

        verify(xmlRepositoryMarshallerMock, times(2)).marshal(xmlUserRootElementMock);
    }

    @Test(expected = IllegalStateException.class)
    public void updatingNonExistingUserThrowsIllegalStateException() throws Exception {
        xmlUserRepository.update(anotherUserMock);
    }

    @Test
    public void gettingUserByUsernameRetrievesTheUserByUsername() throws Exception {
        when(userMock.usernameEquals(SAMPLE_USERNAME)).thenReturn(true);
        xmlUserRepository.persist(userMock);
        assertSame(userMock, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }

    @Test(expected = UserNotFoundException.class)
    public void gettingUserByUsernameUsingNonExistingUsernameThrowsUserNotFoundException() throws Exception {
        when(userMock.usernameEquals(SAMPLE_USERNAME)).thenReturn(true);
        xmlUserRepository.persist(userMock);
        xmlUserRepository.getByUsername(SAMPLE_UNEXISTING_USERNAME);
    }

    @Test
    public void gettingUserByActivationCodeRetrievesUserByActivationCode() throws Exception {
        when(userMock.getActivationCode()).thenReturn(SAMPLE_ACTIVATION_CODE);
        xmlUserRepository.persist(userMock);
        assertSame(userMock, xmlUserRepository.getByActivationCode(SAMPLE_ACTIVATION_CODE));
    }

    @Test(expected = UserNotFoundException.class)
    public void gettingUserByActivationCodeUsingNonExistingActivationCodeThrowsUserNotFoundException()
            throws Exception {
        xmlUserRepository.getByActivationCode(ANOTHER_SAMPLE_ACTIVATION_CODE);
    }
}
