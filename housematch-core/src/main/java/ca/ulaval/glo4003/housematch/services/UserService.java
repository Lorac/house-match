package ca.ulaval.glo4003.housematch.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotActivatedException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.validators.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.UserRegistrationValidator;

public class UserService {

    private UserRepository userRepository;
    private UserActivationService userActivationService;
    private UserRegistrationValidator userCreationValidator;

    public UserService(final UserRepository userRepository, final UserRegistrationValidator userCreationValidator,
            final UserActivationService userActivationService) {
        this.userRepository = userRepository;
        this.userCreationValidator = userCreationValidator;
        this.userActivationService = userActivationService;
    }

    public User getUserByLoginCredentials(String username, String password)
            throws UserNotFoundException, InvalidPasswordException, UserNotActivatedException {
        User user = userRepository.getByUsername(username);
        user.validatePassword(password);
        return user;
    }

    public void registerUser(String username, String email, String password, UserRole role)
            throws UserAlreadyExistsException, UserRegistrationValidationException {
        userCreationValidator.validateUserCreation(username, email, password, role);
        User user = new User(username, email, password, role);
        userRepository.persist(user);
        userActivationService.beginActivation(user);
    }

    public List<UserRole> getPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = Arrays.asList(UserRole.values());

        return userRoles.stream().filter(UserRole::isPubliclyRegistrable).collect(Collectors.toList());
    }
}
