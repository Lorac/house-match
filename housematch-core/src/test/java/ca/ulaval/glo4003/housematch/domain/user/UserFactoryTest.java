package ca.ulaval.glo4003.housematch.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.utils.StringHasher;

public class UserFactoryTest {

    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_PASSWORD = "password";
    private static final String SAMPLE_PASSWORD_HASH = "asd098fsdfgw4";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    private StringHasher stringHasherMock;
    private UserObserver userObserverMock;
    private List<UserObserver> userObservers = new ArrayList<>();

    private UserFactory userFactory;

    @Before
    public void init() {
        initMocks();
        initStubs();
        userObservers.add(userObserverMock);
        userFactory = new UserFactory(stringHasherMock, userObservers);
    }

    private void initMocks() {
        stringHasherMock = mock(StringHasher.class);
        userObserverMock = mock(UserObserver.class);
    }

    private void initStubs() {
        when(stringHasherMock.hash(SAMPLE_PASSWORD)).thenReturn(SAMPLE_PASSWORD_HASH);
    }

    @Test
    public void userFactoryCreatesUserWithTheSpecifiedAttributes() {
        User user = userFactory.createUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);

        assertEquals(SAMPLE_USERNAME, user.getUsername());
        assertEquals(SAMPLE_PASSWORD_HASH, user.getPasswordHash());
        assertEquals(SAMPLE_EMAIL, user.getEmail());
        assertEquals(SAMPLE_ROLE, user.getRole());
    }

    @Test
    public void userFactoryRegistersTheSharedObserverToTheUser() {
        User user = userFactory.createUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.isObserverRegistered(userObserverMock));
    }
}
