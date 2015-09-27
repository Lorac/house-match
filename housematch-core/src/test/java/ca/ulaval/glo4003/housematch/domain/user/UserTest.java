package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.InvalidValueException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final UserRole ANOTHER_SAMPLE_ROLE = UserRole.BUYER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String ANOTHER_SAMPLE_PASSWORD = "PASSWORD5678";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String ANOTHER_SAMPLE_EMAIL = "email2@hotmail.com";
    private static final String SAMPLE_INVALID_EMAIL = "abc@abc";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String SAMPLE_CAPITALIZED_USERNAME = "ALICE";
    private static final String ANOTHER_SAMPLE_USERNAME = "Bob";
    private static final String SAMPLE_BLANK_EXPRESSION = "  ";

    private User user;

    @Before
    public void init() throws Exception {
        user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test
    public void equalsMethodShouldConsiderUsersWithTheSameUsernameAsEqual() {
        User anotherUser = new User(SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD,
                ANOTHER_SAMPLE_ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void givenTheSameInstanceOfTheUserWhenComparingThemThenTheyShouldBeEqual() {
        // noinspection EqualsWithItself
        assertTrue(user.equals(user));
    }

    @Test
    public void equalsMethodShouldConsiderUsersWithDifferentUsernameAsDifferent() {
        User anotherUser = new User(ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.equals(anotherUser));
    }

    @Test
    public void equalsMethodShouldConsiderUsersHavingDifferentUsernameCapitalizationAsEqual() {
        User anotherUser = new User(SAMPLE_CAPITALIZED_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void usernameEqualsMethodShouldConsiderUsersWithTheSameUsernameAsEqual() {
        User anotherUser = new User(SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD,
                ANOTHER_SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameEqualsMethodShouldConsiderUsersWithDifferentUsernameAsDifferent() {
        User anotherUser = new User(ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameEqualsMethodShouldConsiderUsersHavingDifferentUsernameCapitalizationAsEqual() {
        User anotherUser = new User(SAMPLE_CAPITALIZED_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test(expected = InvalidValueException.class)
    public void settingUsernameUsingBlankExpressionThrowsInvalidValueException() {
        user.setUsername(SAMPLE_BLANK_EXPRESSION);
    }

    @Test(expected = InvalidValueException.class)
    public void settingEmailUsingAnInvalidEmailFormatThrowsInvalidValueException() {
        user.setEmail(SAMPLE_INVALID_EMAIL);
    }

    @Test(expected = InvalidValueException.class)
    public void settingEmailUsingBlankExpressionThrowsInvalidValueException() {
        user.setEmail(SAMPLE_BLANK_EXPRESSION);
    }

    @Test(expected = InvalidValueException.class)
    public void settingPasswordUsingBlankExpressionThrowsInvalidValueException() {
        user.setPassword(SAMPLE_BLANK_EXPRESSION);
    }

    @Test
    public void validatingTheRightPasswordDoesNotThrowAnException() throws Exception {
        user.isPasswordValid(SAMPLE_PASSWORD);
    }

    @Test
    public void validatingTheWrongPasswordThrowsInvalidPasswordException() throws Exception {
        assertFalse(user.isPasswordValid(ANOTHER_SAMPLE_PASSWORD));
    }

    @Test
    public void newUserIsNotActivated() {
        assertFalse(user.isActivated());
    }

    @Test
    public void activatedUserIsActivated() throws Exception {
        user.activate();
        user.validateActivation();
    }

    @Test(expected = UserNotActivatedException.class)
    public void givenAUserNotActivatedWhenValidatingHisActivitionThenItWillThrow() throws Exception {
        user.validateActivation();
    }
}