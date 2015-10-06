package ca.ulaval.glo4003.housematch.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.UUID;

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
    private static final String SAMPLE_ADDRESS = "123 potato street";
    private static final String SAMPLE_POST_CODE = "X1X1X1";
    private static final String SAMPLE_REGION = "Quebec";
    private static final String SAMPLE_COUNTRY = "Canada";
    private static final String SAMPLE_CITY = "Potatown";
    private static final UUID SAMPLE_CODE = UUID.randomUUID();

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
    public void usernameComparisonShouldConsiderUsersWithTheSameUsernameAsEqual() throws Exception {
        User anotherUser = new User(SAMPLE_USERNAME, ANOTHER_SAMPLE_EMAIL, ANOTHER_SAMPLE_PASSWORD,
                ANOTHER_SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameComparisonShouldConsiderUsersWithDifferentUsernameAsDifferent() throws Exception {
        User anotherUser = new User(ANOTHER_SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertFalse(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void usernameComparisonShouldConsiderUsersHavingDifferentUsernameCapitalizationAsEqual() throws Exception {
        User anotherUser = new User(SAMPLE_CAPITALIZED_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        assertTrue(user.usernameEquals(anotherUser.getUsername()));
    }

    @Test
    public void settingTheUsernameSetsTheSpecifiedUsername() throws Exception {
        user.setUsername(SAMPLE_USERNAME);
        assertEquals(SAMPLE_USERNAME, user.getUsername());
    }

    @Test
    public void settingTheEmailSetsTheSpecifiedEmail() throws Exception {
        user.setEmail(SAMPLE_EMAIL);
        assertEquals(SAMPLE_EMAIL, user.getEmail());
    }

    @Test
    public void settingThePasswordSetsTheSpecifiedPassword() throws Exception {
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
    public void settingTheRoleSetsTheSpecifiedRole() {
        user.setRole(SAMPLE_ROLE);
        assertEquals(SAMPLE_ROLE, user.getRole());
    }

    @Test
    public void userShouldNotHaveTheSpecifiedRoleWhenThatRoleHasNotBeenAdded() {
        user.setRole(SAMPLE_ROLE);
        assertFalse(user.hasRole(ANOTHER_SAMPLE_ROLE));
    }

    @Test
    public void userShouldHaveTheSpecifiedRoleWhenThatRoleHasBeenAdded() {
        user.setRole(SAMPLE_ROLE);
        assertTrue(user.hasRole(SAMPLE_ROLE));
    }

    @Test
    public void newUserIsNotActivatedByDefault() {
        assertFalse(user.isActivated());
    }

    @Test
    public void activationOfTheUserActivatesTheUser() throws Exception {
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

    @Test
    public void settingTheAddressSetsTheSpecifiedAddress() throws Exception {
        user.setAddress(SAMPLE_ADDRESS);
        assertEquals(SAMPLE_ADDRESS, user.getAddress());
    }

    @Test
    public void settingThePostCodeSetsTheSpecifiedPostCode() throws Exception {
        user.setPostCode(SAMPLE_POST_CODE);
        assertEquals(SAMPLE_POST_CODE, user.getPostCode());
    }

    @Test
    public void settingTheRegionSetsTheSpecifiedRegion() throws Exception {
        user.setRegion(SAMPLE_REGION);
        assertEquals(SAMPLE_REGION, user.getRegion());
    }

    @Test
    public void settingTheCountrySetsTheSpecifiedCountry() throws Exception {
        user.setCountry(SAMPLE_COUNTRY);
        assertEquals(SAMPLE_COUNTRY, user.getCountry());
    }

    @Test
    public void settingTheCitySetsTheSpecifiedCity() throws Exception {
        user.setCity(SAMPLE_CITY);
        assertEquals(SAMPLE_CITY, user.getCity());
    }

    @Test
    public void settingTheModifcationOnSetsTheSpecifiedModificationCode() throws Exception {
        user.startModification(SAMPLE_CODE);
        assertEquals(SAMPLE_CODE, user.getModificationCode());
    }

    @Test
    public void endingTheModifcationOnRemovesTheModificationCode() throws Exception {
        user.endModification();
        assertNull(user.getActivationCode());
    }

    @Test
    public void settingTheTemporaryEmailSetsTheSpecifiedTemporaryEmail() throws Exception {
        user.setTemporaryEmail(SAMPLE_EMAIL);
        assertEquals(SAMPLE_EMAIL, user.getTemporaryEmail());
    }
}