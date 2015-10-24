package ca.ulaval.glo4003.housematch.validators.user;

import static org.junit.Assert.fail;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserRegistrationValidatorTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String SAMPLE_INVALID_EMAIL = "abc@abc";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String[] SAMPLE_VALID_USERNAMES = { "abc", "ab-c", "ab_c", "a0c" };
    private static final String[] SAMPLE_INVALID_USERNAMES = { "ab", "4abc", "_abc", "-abc", "abc$" };
    private static final String SAMPLE_TOO_SHORT_USERNAME = StringUtils.repeat("a", UserRegistrationValidator.MIN_USERNAME_LENGTH - 1);
    private static final String SAMPLE_TOO_LONG_USERNAME = StringUtils.repeat("a", UserRegistrationValidator.MAX_USERNAME_LENGTH + 1);
    private static final String SAMPLE_TOO_SHORT_PASSWORD = StringUtils.repeat("a", UserRegistrationValidator.MIN_PASSWORD_LENGTH - 1);
    private static final String SAMPLE_TOO_LONG_PASSWORD = StringUtils.repeat("a", UserRegistrationValidator.MAX_PASSWORD_LENGTH + 1);
    private static final String SAMPLE_BLANK_EXPRESSION = "  ";

    private UserRegistrationValidator userRegistrationValidator;

    @Before
    public void init() throws Exception {
        userRegistrationValidator = new UserRegistrationValidator();
    }

    @Test
    public void userRegistrationUsingValidValuesPassesValidation() throws Exception {
        try {
            userRegistrationValidator.validateUserRegistration(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void userRegistrationUsingValidUsernamesPassesValidation() throws Exception {
        for (String validUsername : SAMPLE_VALID_USERNAMES) {
            try {
                userRegistrationValidator.validateUserRegistration(validUsername, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
            } catch (Exception e) {
                fail();
            }
        }
    }

    @Test
    public void userRegistrationUsingInvalidUsernamesThrowsUserRegistrationValidationException() throws Exception {
        for (String invalidUsername : SAMPLE_INVALID_USERNAMES) {
            try {
                userRegistrationValidator.validateUserRegistration(invalidUsername, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
                fail(String.format("Username '%s' passed validation when it should not have.", invalidUsername));
            } catch (UserRegistrationValidationException e) {
                continue;
            }
        }
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingTooShortUsernameThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_TOO_SHORT_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingTooLongUsernameThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_TOO_LONG_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingTooShortPasswordThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_TOO_SHORT_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingTooLongPasswordThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_TOO_LONG_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingBlankUsernameThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_BLANK_EXPRESSION, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingAnInvalidEmailFormatThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_USERNAME, SAMPLE_INVALID_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingBlankEmailThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_USERNAME, SAMPLE_BLANK_EXPRESSION, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingBlankPasswordThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_BLANK_EXPRESSION, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationWithNoRoleSpecifiedThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, null);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationAsAnAdministratorThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserRegistration(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, UserRole.ADMINISTRATOR);
    }
}
