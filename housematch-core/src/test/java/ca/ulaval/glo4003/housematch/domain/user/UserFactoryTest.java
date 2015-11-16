package ca.ulaval.glo4003.housematch.domain.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    private UserFactory userFactory;

    @Before
    public void init() {
        stringHasherMock = mock(StringHasher.class);
        userObserverMock = mock(UserObserver.class);
        when(stringHasherMock.hash(SAMPLE_PASSWORD)).thenReturn(SAMPLE_PASSWORD_HASH);
        userFactory = new UserFactory(stringHasherMock, userObserverMock);
    }

    @Test
    public void userFactoryCreatesUserWithTheSpecifiedAttributes() {
        User user = userFactory.createUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);

        assertEquals(SAMPLE_USERNAME, user.getUsername());
        assertEquals(SAMPLE_PASSWORD_HASH, user.getPasswordHash());
        assertEquals(SAMPLE_EMAIL, user.getEmail());
        assertEquals(SAMPLE_ROLE, user.getRole());
    }
}
