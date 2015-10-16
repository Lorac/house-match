package ca.ulaval.glo4003.housematch.domain.user;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UserFactoryTest {

    private static final String SAMPLE_USERNAME = "username1";
    private static final String SAMPLE_EMAIL = "test@test.com";
    private static final String SAMPLE_PASSWORD = "password1234";
    private static final UserRole SAMPLE_ROLE = UserRole.BUYER;

    private UserFactory userFactory;

    @Before
    public void init() {
        userFactory = new UserFactory();
    }

    @Test
    public void userFactoryCreatesUserWithTheSpecifiedAttributes() {
        User user = userFactory.getUser(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);

        assertEquals(SAMPLE_USERNAME, user.getUsername());
        assertEquals(SAMPLE_PASSWORD, user.getPassword());
        assertEquals(SAMPLE_EMAIL, user.getEmail());
        assertEquals(SAMPLE_ROLE, user.getRole());
    }
}
