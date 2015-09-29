package ca.ulaval.glo4003.housematch.validators;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserCreationValidatorTest {

    private static final UserRole SAMPLE_ROLE = UserRole.SELLER;
    private static final String SAMPLE_PASSWORD = "PASSWORD1234";
    private static final String SAMPLE_EMAIL = "email@hotmail.com";
    private static final String SAMPLE_INVALID_EMAIL = "abc@abc";
    private static final String SAMPLE_USERNAME = "Alice";
    private static final String SAMPLE_BLANK_EXPRESSION = "  ";

    private UserCreationValidator userCreationValidator;

    @Before
    public void init() throws Exception {
        userCreationValidator = new UserCreationValidator();
    }

    @Test
    public void creationOfUserUsingValidValuesPassesValidation() throws Exception {
        try {
            userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = UserCreationValidationException.class)
    public void userCreationUsingBlankUsernameThrowsUserCreationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_BLANK_EXPRESSION, SAMPLE_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserCreationValidationException.class)
    public void userCreationUsingAnInvalidEmailFormatThrowsUserCreationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_INVALID_EMAIL, SAMPLE_PASSWORD, SAMPLE_ROLE);
    }

    @Test(expected = UserCreationValidationException.class)
    public void userCreationUsingBlankEmailThrowsUserCreationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_BLANK_EXPRESSION, SAMPLE_PASSWORD,
                SAMPLE_ROLE);
    }

    @Test(expected = UserCreationValidationException.class)
    public void userCreationUsingBlankPasswordThrowsUserCreationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_BLANK_EXPRESSION, SAMPLE_ROLE);
    }

    @Test(expected = UserCreationValidationException.class)
    public void creationOfUserAsAnAdministratorThrowsUserCreationValidationException() throws Exception {
        userCreationValidator.validateUserCreation(SAMPLE_USERNAME, SAMPLE_EMAIL, SAMPLE_PASSWORD,
                UserRole.ADMINISTRATOR);
    }
}
