package ca.ulaval.glo4003.housematch.persistence.repositories;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.persistence.XmlRootElementNode;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlUserRepositoryTest {
    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_UNEXISTING_USERNAME = "username2";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_PASSWORD = "password1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    private XmlRepositoryMarshaller xmlRepositoryMarshallerMock;
    private XmlRootElementNode xmlRootElementNodeMock;
    private XmlUserRepository xmlUserRepository;

    @Before
    public void init() {
        initMocks();
        stubMethods();
        xmlUserRepository = new XmlUserRepository(xmlRepositoryMarshallerMock);
    }

    private void stubMethods() {
        when(xmlRepositoryMarshallerMock.getRootElementNode()).thenReturn(xmlRootElementNodeMock);
        when(xmlRootElementNodeMock.getUsers()).thenReturn(new ArrayList<>());
    }

    private void initMocks() {
        xmlRepositoryMarshallerMock = mock(XmlRepositoryMarshaller.class);
        xmlRootElementNodeMock = mock(XmlRootElementNode.class);
    }

    @Test
    public void persistingUserPersistsUserToRepository() throws Exception {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        assertSame(user, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void persistingUserWhichAlreadyExistsThrowsUserAlreadyExistsException() throws Exception {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        User user2 = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        xmlUserRepository.persist(user2);
    }

    @Test
    public void gettingUserByUsernameRetrievesTheUserByUsername() throws Exception {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        assertSame(user, xmlUserRepository.getByUsername(SAMPLE_USERNAME));
    }

    @Test(expected = UserNotFoundException.class)
    public void gettingUserByUsernameUsingNonExistingUsernameThrowsUserNotFoundException() throws Exception {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        assertSame(user, xmlUserRepository.getByUsername(SAMPLE_UNEXISTING_USERNAME));
    }

    @Test
    public void gettingUserByHashCodeRetrievesUserByHashCode() throws Exception {
        User user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        xmlUserRepository.persist(user);
        assertSame(user, xmlUserRepository.getByHashCode(SAMPLE_USERNAME.hashCode()));
    }

    @Test(expected = UserNotFoundException.class)
    public void gettingUserByHashCodeUsingNonExistingHashCodeThrowsUserNotFoundException() throws Exception {
        xmlUserRepository.getByHashCode(SAMPLE_UNEXISTING_USERNAME.hashCode());
    }
}
