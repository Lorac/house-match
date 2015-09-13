package ca.ulaval.glo4003.housematch.domain.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private static final UserRole A_ROLE = UserRole.SELLER;
    private static final String PASSWORD = "PASSWORD1234";
    private static final String EMAIL = "email@hotmail.com";
    private static final String USERNAME = "Alice";
    private static final String ANOTHER_USERNAME = "Bob";
    private static final String CAPITALIZE_USER = "ALICE";
    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User(USERNAME, EMAIL, PASSWORD, A_ROLE);
    }

    @Test
    public void whenComparingTheSameUserItShouldBeTheSameUser() {
        assertTrue(user.compareTo(user));
    }

    @Test
    public void whenComparingDifferentUsersItShouldBeDifferentUser() {
        User anotherUser = new User(ANOTHER_USERNAME, EMAIL, PASSWORD, A_ROLE);
        assertFalse(user.compareTo(anotherUser));
    }

    @Test
    public void whenComparingUserWithUsernameDifferentCapitalizationItShouldStillBeTheSameUser() {
        User anotherUser = new User(CAPITALIZE_USER, EMAIL, PASSWORD, A_ROLE);
        assertTrue(user.compareTo(anotherUser));
    }
}