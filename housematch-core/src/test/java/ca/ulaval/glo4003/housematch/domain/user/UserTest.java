package ca.ulaval.glo4003.housematch.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final UserRole ANOTHER_SAMPLE_ROLE = UserRole.BUYER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String ANOTHER_SAMPLE_PASSWORD = "PASSWORD5678";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String ANOTHER_SAMPLE_EMAIL = "email2@hotmail.com";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String SAMPLE_CAPITALIZED_USERNAME = "ALICE";
    private static final String ANOTHER_SAMPLE_USERNAME = "Bob";
    private static final Object SAMPLE_OBJECT = new Object();

    private User user;

    @Before
    public void init() throws Exception {
        user = new User(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test
    public void usersWithTheSameUsernameShouldBeConsideredAsEqual() throws Exception {
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
    public void usersWithDifferentUsernameShouldBeConsideredAsDifferent() throws Exception {
        User anotherUser = new User(ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.equals(anotherUser));
    }

    @Test
    public void usersHavingDifferentUsernameCapitalizationShouldBeConsideredAsEqual() throws Exception {
        User anotherUser = new User(SAMPLE_CAPITALIZED_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void usernameEqualsMethodShouldConsiderUsersWithTheSameUsernameAsEqual() throws Exception {
        User anotherUser = new User(SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD,
                ANOTHER_SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameEqualsMethodShouldConsiderUsersWithDifferentUsernameAsDifferent() throws Exception {
        User anotherUser = new User(ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameEqualsMethodShouldConsiderUsersHavingDifferentUsernameCapitalizationAsEqual() throws Exception {
        User anotherUser = new User(SAMPLE_CAPITALIZED_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void settingUsernameSetsTheSpecifiedUsername() throws Exception {
        user.setUsername(SAMPLE_USERNAME);
        assertEquals(SAMPLE_USERNAME, user.getUsername());
    }

    @Test
    public void settingEmailSetsTheSpecifiedEmail() throws Exception {
        user.setEmail(SAMPLE_EMAIL);
        assertEquals(SAMPLE_EMAIL, user.getEmail());
    }

    @Test
    public void settingPasswordSetsTheSpecifiedPassword() throws Exception {
        user.setPassword(SAMPLE_PASSWORD);
        assertEquals(SAMPLE_PASSWORD, user.getPassword());
    }

    @Test
    public void validationOfTheRightPasswordDoesNotThrowAnException() throws Exception {
        try {
            user.validatePassword(SAMPLE_PASSWORD);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = InvalidPasswordException.class)
    public void validationOfTheWrongPasswordThrowsInvalidPasswordException() throws Exception {
        user.validatePassword(ANOTHER_SAMPLE_PASSWORD);
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
    public void newUserIsNotActivatedByDefault() {
        assertFalse(user.isActivated());
    }

    @Test
    public void activationOfUserActivatesTheUser() throws Exception {
        user.activate();
        try {
            user.validateActivation();
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = UserNotActivatedException.class)
    public void activationValidationOnNonActivatedUserThrowsUserNotActivatedException() throws Exception {
        user.validateActivation();
    }
}