package ca.ulaval.glo4003.housematch.persistence.user;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private static final String ANOTHER_SAMPLE_USERNAME = "username2";
    private static final UUID SAMPLE_ACTIVATION_CODE = UUID.randomUUID();
    private static final UUID ANOTHER_SAMPLE_ACTIVATION_CODE = UUID.randomUUID();

    private XmlRepositoryMarshaller<XmlUserRootElement> xmlRepositoryMarshallerMock;
    private XmlUserAdapter xmlUserAdapterMock;
    private XmlUserRootElement xmlUserRootElementMock;
    private User userMock;
    private User anotherUserMock;

    private XmlUserRepository xmlUserRepository;

    @Before
    public void init() {
        initMocks();
        initStubs();
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

    private void initStubs() {
        when(xmlRepositoryMarshallerMock.unmarshal()).thenReturn(xmlUserRootElementMock);
        when(userMock.getUsername()).thenReturn(SAMPLE_USERNAME);
        when(anotherUserMock.getUsername()).thenReturn(ANOTHER_SAMPLE_USERNAME);
        when(userMock.getActivationCode()).thenReturn(SAMPLE_ACTIVATION_CODE);

    }

    @Test
    public void persistingUserPersistsUserToRepository() throws Exception {
        when(userMock.usernameEquals(SAMPLE_USERNAME)).thenReturn(true);
        xmlUserRepository.persist(userMock);
        assertSame(userMock, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }

    @Test
    public void persistingUserMarshallsTheUsersInTheRepositoryMarshaller() throws Exception {
        xmlUserRepository.persist(userMock);

        verify(xmlUserRootElementMock).setUsers(anyCollectionOf(User.class));
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
        xmlUserRepository.update(userMock);
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
        xmlUserRepository.getByUsername(ANOTHER_SAMPLE_USERNAME);
    }

    @Test
    public void gettingUserByActivationCodeRetrievesUserByActivationCode() throws Exception {
        when(userMock.getActivationCode()).thenReturn(SAMPLE_ACTIVATION_CODE);
        xmlUserRepository.persist(userMock);
        assertSame(userMock, xmlUserRepository.getByActivationCode(SAMPLE_ACTIVATION_CODE));
    }

    @Test(expected = UserNotFoundException.class)
    public void gettingUserByActivationCodeUsingNonExistingActivationCodeThrowsUserNotFoundException() throws Exception {
        xmlUserRepository.getByActivationCode(ANOTHER_SAMPLE_ACTIVATION_CODE);
    }

    @Test
    public void gettingAllUsersGetsAllTheUsers() throws Exception {
        xmlUserRepository.persist(userMock);
        xmlUserRepository.persist(anotherUserMock);

        List<User> users = xmlUserRepository.getAll();

        assertThat(users, containsInAnyOrder(userMock, anotherUserMock));
    }

    @Test
    public void checkingIfRepositoryIsEmptyReturnsTrueWhenRepositoryIsEmpty() {
        assertTrue(xmlUserRepository.isEmpty());
    }

    @Test
    public void checkingIfRepositoryIsEmptyReturnsFalseWhenRepositoryIsNotEmpty() throws Exception {
        xmlUserRepository.persist(userMock);
        assertFalse(xmlUserRepository.isEmpty());
    }
}
