package ca.ulaval.glo4003.housematch.validators.user;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class UserRegistrationValidator {

    public void validateUserCreation(String username, String email, String password, UserRole role)
            throws UserRegistrationValidationException {
        if (StringUtils.isBlank(username)) {
            throw new UserRegistrationValidationException("Username cannot be blank.");
        } else if (StringUtils.isBlank(email)) {
            throw new UserRegistrationValidationException("Email cannot be blank.");
        } else if (!EmailValidator.getInstance().isValid(email)) {
            throw new UserRegistrationValidationException("The email format is not valid.");
        } else if (StringUtils.isBlank(password)) {
            throw new UserRegistrationValidationException("Password cannot be blank.");
        } else if (role == null) {
            throw new UserRegistrationValidationException("Role must be specified.");
        } else if (role.equals(UserRole.ADMINISTRATOR)) {
            throw new UserRegistrationValidationException("Can't create user as an administrator.");
        }

    }
}
