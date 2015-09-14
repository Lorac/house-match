package ca.ulaval.glo4003.housematch.domain.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private static final UserRole ROLE = UserRole.SELLER;
    private static final UserRole ANOTHER_ROLE = UserRole.BUYER;
    private static final String PASSWORD = "PASSWORD1234";
    private static final String ANOTHER_PASSWORD = "PASSWORD5678";
    private static final String EMAIL = "email@hotmail.com";
    private static final String ANOTHER_EMAIL = "email2@hotmail.com";
    private static final String INVALID_EMAIL = "abc@abc";
    private static final String USERNAME = "Alice";
    private static final String CAPITALIZED_USERNAME = "ALICE";
    private static final String ANOTHER_USERNAME = "Bob";
    private static final String BLANK_EXPRESSION = "  ";

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User(USERNAME, EMAIL, PASSWORD, ROLE);
    }

    @Test
    public void usersWithTheSameUsernameShouldBeEqual() {
        User anotherUser = new User(USERNAME, ANOTHER_EMAIL, ANOTHER_PASSWORD, ANOTHER_ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void usersWithDifferentUsernameShouldNotBeEqual() {
        User anotherUser = new User(ANOTHER_USERNAME, EMAIL, PASSWORD, ROLE);
        assertFalse(user.equals(anotherUser));
    }

    @Test
    public void usersHavingDifferentUsernameCapitalizationShouldBeEqual() {
        User anotherUser = new User(CAPITALIZED_USERNAME, EMAIL, PASSWORD, ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void usersWithTheSameUsernameShouldConsiderUsernameAsEqual() {
        User anotherUser = new User(USERNAME, ANOTHER_EMAIL, ANOTHER_PASSWORD, ANOTHER_ROLE);
        assertTrue(user.isUsernameEqual(anotherUser.getUsername()));
    }

    @Test
    public void usersWithDifferentUsernameShouldNotConsiderUsernameAsEqual() {
        User anotherUser = new User(ANOTHER_USERNAME, EMAIL, PASSWORD, ROLE);
        assertFalse(user.isUsernameEqual(anotherUser.getUsername()));
    }

    @Test
    public void usersHavingDifferentUsernameCapitalizationShouldConsiderUsernameAsEqual() {
        User anotherUser = new User(CAPITALIZED_USERNAME, EMAIL, PASSWORD, ROLE);
        assertTrue(user.isUsernameEqual(anotherUser.getUsername()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void settingUsernameUsingBlankExpressionThrowsIllegalArgumentException() {
        user.setUsername(BLANK_EXPRESSION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void settingEmailUsingAnInvalidEmailFormatThrowsIllegalArgumentException() {
        user.setEmail(INVALID_EMAIL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void settingEmailUsingBlankExpressionThrowsIllegalArgumentException() {
        user.setEmail(BLANK_EXPRESSION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void settingPasswordUsingBlankExpressionThrowsIllegalArgumentException() {
        user.setPassword(BLANK_EXPRESSION);
    }

    @Test
    public void passwordValidationValidatesTheRightPassword() {
        user.validatePassword(PASSWORD);
    }

    @Test(expected = InvalidPasswordException.class)
    public void passwordValidationUsingWrongPasswordThrowsInvalidPasswordException() {
        user.validatePassword(ANOTHER_PASSWORD);
    }
}