package ca.ulaval.glo4003.housematch.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.InvalidValueException;

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
    private static final Object SAMPLE_OBJECT = new Object();

    private User user;

    @Before
    public void init() throws Exception {
        user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test
    public void usersWithTheSameUsernameShouldBeConsideredAsEqual() {
        User anotherUser = new User(SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD,
                ANOTHER_SAMPLE_ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void userComparedWithItselfShouldBeConsideredAsEqual() {
        assertTrue(user.equals(user));
    }

    @Test
    public void userComparedWithAnotherObjectShouldNotBeConsideredAsEqual() {
        assertFalse(user.equals(SAMPLE_OBJECT));
    }

    @Test
    public void usersWithDifferentUsernameShouldBeConsideredAsDifferent() {
        User anotherUser = new User(ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.equals(anotherUser));
    }

    @Test
    public void usersHavingDifferentUsernameCapitalizationShouldBeConsideredAsEqual() {
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

    @Test
    public void settingUsernameSetsTheSpecifiedUsername() {
        user.setUsername(SAMPLE_USERNAME);
        assertEquals(SAMPLE_USERNAME, user.getUsername());
    }

    @Test(expected = InvalidValueException.class)
    public void settingUsernameUsingBlankExpressionThrowsInvalidValueException() {
        user.setUsername(SAMPLE_BLANK_EXPRESSION);
    }

    @Test
    public void settingEmailSetsTheSpecifiedEmail() {
        user.setEmail(SAMPLE_EMAIL);
        assertEquals(SAMPLE_EMAIL, user.getEmail());
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
    public void settingPasswordSetsTheSpecifiedPassword() {
        user.setPassword(SAMPLE_PASSWORD);
        assertEquals(SAMPLE_PASSWORD, user.getPassword());
    }

    @Test
    public void validationOfTheRightPasswordDoesNotThrowAnException() throws Exception {
        user.isPasswordValid(SAMPLE_PASSWORD);
    }

    @Test
    public void settingRoleSetsTheSpecifiedRole() {
        user.setRole(SAMPLE_ROLE);
        assertEquals(SAMPLE_ROLE, user.getRole());
    }

    @Test
    public void hasRoleMethodShouldReturnFalseWhenUserDoesNotHaveTheSpecifiedRole() {
        user.setRole(SAMPLE_ROLE);
        assertFalse(user.hasRole(ANOTHER_SAMPLE_ROLE));
    }

    @Test
    public void hasRoleMethodShouldReturnTrueWhenUserHasTheSpecifiedRole() {
        user.setRole(SAMPLE_ROLE);
        assertTrue(user.hasRole(SAMPLE_ROLE));
    }

    @Test
    public void validationOfTheWrongPasswordThrowsInvalidPasswordException() throws Exception {
        assertFalse(user.isPasswordValid(ANOTHER_SAMPLE_PASSWORD));
    }

    @Test
    public void newUserIsNotActivatedByDefault() {
        assertFalse(user.isActivated());
    }

    @Test
    public void activationOfUserActivatesTheUser() throws Exception {
        user.activate();
        user.validateActivation();
    }

    @Test(expected = UserNotActivatedException.class)
    public void activationValidationOnNonActivatedUserThrowsUserNotActivatedException() throws Exception {
        user.validateActivation();
    }
}