package ca.ulaval.glo4003.housematch.validators.user;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserRegistrationValidator {

    private static final String USERNAME_VALIDATION_REGEX = "^[A-Z][A-Z0-9_-]*$";
    static final Integer MIN_USERNAME_LENGTH = 3;
    static final Integer MAX_USERNAME_LENGTH = 32;
    static final Integer MIN_PASSWORD_LENGTH = 8;
    static final Integer MAX_PASSWORD_LENGTH = 32;

    private Pattern userNameValidationRegExPattern;

    public UserRegistrationValidator() {
        userNameValidationRegExPattern = Pattern.compile(USERNAME_VALIDATION_REGEX, Pattern.CASE_INSENSITIVE);
    }

    public void validateUserRegistration(String username, String email, String password, UserRole role)
            throws UserRegistrationValidationException {
        validateUsername(username);
        validateEmail(email);
        validatePassword(password);
        validateRole(role);
    }

    private void validateUsername(String username) throws UserRegistrationValidationException {
        if (StringUtils.isBlank(username)) {
            throw new UserRegistrationValidationException("Username cannot be blank.");
        } else if (!userNameValidationRegExPattern.matcher(username).matches()) {
            throw new UserRegistrationValidationException(
                    "Username may only contain letters (a-z), digits (0-9), underscores (_) or dashes (-) and must begin with a letter.");
        } else if (username.length() < MIN_USERNAME_LENGTH) {
            throw new UserRegistrationValidationException(
                    String.format("Username must contain at least %d characters.", MIN_USERNAME_LENGTH));
        } else if (username.length() > MAX_USERNAME_LENGTH) {
            throw new UserRegistrationValidationException(
                    String.format("Username length must not exceed %d characters.", MAX_USERNAME_LENGTH));
        }
    }

    private void validatePassword(String password) throws UserRegistrationValidationException {
        if (StringUtils.isBlank(password)) {
            throw new UserRegistrationValidationException("Password cannot be blank.");
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationValidationException(
                    String.format("Password must contain at least %d characters.", MIN_PASSWORD_LENGTH));
        } else if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new UserRegistrationValidationException(
                    String.format("Password length must not exceed %d characters.", MAX_PASSWORD_LENGTH));
        }
    }

    private void validateEmail(String email) throws UserRegistrationValidationException {
        if (StringUtils.isBlank(email)) {
            throw new UserRegistrationValidationException("Email cannot be blank.");
        } else if (!EmailValidator.getInstance().isValid(email)) {
            throw new UserRegistrationValidationException("The email format is not valid.");
        }
    }

    private void validateRole(UserRole role) throws UserRegistrationValidationException {
        if (role == null) {
            throw new UserRegistrationValidationException("Role must be specified.");
        } else if (role.equals(UserRole.ADMINISTRATOR)) {
            throw new UserRegistrationValidationException("Registering as an administrator is not permitted.");
        }
    }
}
