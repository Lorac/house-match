package ca.ulaval.glo4003.housematch.validators;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserRegistrationValidatorTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String SAMPLE_INVALID_EMAIL = "abc@abc";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String SAMPLE_BLANK_EXPRESSION = "  ";

    private UserRegistrationValidator userCreationValidator;

    @Before
    public void init() throws Exception {
        userCreationValidator = new UserRegistrationValidator();
    }

    @Test
    public void userRegistrationUsingValidValuesPassesValidation() throws Exception {
        try {
            userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingBlankUsernameThrowsUserRegistrationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_BLANK_EXPRESSION, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingAnInvalidEmailFormatThrowsUserRegistrationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_INVALID_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingBlankEmailThrowsUserRegistrationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_BLANK_EXPRESSION, SAMPLE_PASSWORD,
                SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationUsingBlankPasswordThrowsUserRegistrationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_BLANK_EXPRESSION, SAMPLE_ROLE);
    }

    @Test(expected = UserRegistrationValidationException.class)
    public void userRegistrationAsAnAdministratorThrowsUserRegistrationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD,
                UserRole.ADMINISTRATOR);
    }
}
