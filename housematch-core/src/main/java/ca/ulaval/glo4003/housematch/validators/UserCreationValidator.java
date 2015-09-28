package ca.ulaval.glo4003.housematch.validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class UserCreationValidator {

    public void validateUserCreation(String username, String email, String password, UserRole role)
            throws UserCreationValidationException {
        if (StringUtils.isBlank(username)) {
            throw new UserCreationValidationException("Username cannot be blank.");
        } else if (StringUtils.isBlank(email)) {
            throw new UserCreationValidationException("Email cannot be blank.");
        } else if (!EmailValidator.getInstance(false).isValid(email)) {
            throw new UserCreationValidationException("The email format is not valid.");
        } else if (StringUtils.isBlank(password)) {
            throw new UserCreationValidationException("Password cannot be blank.");
        } else if (role.equals(UserRole.ADMINISTRATOR)) {
            throw new UserCreationValidationException("Can't register as an administrator.");
        }

    }
}
