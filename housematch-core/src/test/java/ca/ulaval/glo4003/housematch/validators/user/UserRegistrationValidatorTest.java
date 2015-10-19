package ca.ulaval.glo4003.housematch.validators.user;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class UserRegistrationValidatorTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String SAMPLE_INVALID_EMAIL = "abc@abc";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String SAMPLE_BLANK_EXPRESSION = "  ";

    private UserRegistrationValidator userRegistrationValidator;

    @Before
    public void init() throws Exception {
        userRegistrationValidator = new UserRegistrationValidator();
    }

    @Test
    public void userRegistrationUsingValidValuesPassesValidation() throws Exception {
        try {
            userRegistrationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingBlankUsernameThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserCreation(SAMPLE_BLANK_EXPRESSION, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingAnInvalidEmailFormatThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_INVALID_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingBlankEmailThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_BLANK_EXPRESSION, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingBlankPasswordThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_BLANK_EXPRESSION, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationWithNoRoleSpecifiedThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, null);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationAsAnAdministratorThrowsUserRegistrationValidationException() throws Exception {
        userRegistrationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, UserRole.ADMINISTRATOR);
    }
}
