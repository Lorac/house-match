package ca.ulaval.glo4003.housematch.services.user;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.email.MailSender;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidator;

public class UserService {

    private UserRepository userRepository;
    private UserActivationService userActivationService;
    private UserRegistrationValidator userCreationValidator;

    public UserService(final UserRepository userRepository, final UserRegistrationValidator userCreationValidator,
            final UserActivationService userActivationService, final MailSender mailSender) {
        this.userRepository = userRepository;
        this.userCreationValidator = userCreationValidator;
        this.userActivationService = userActivationService;
    }

    public User getUserByLoginCredentials(String username, String password) throws UserServiceException {
        try {
            User user = userRepository.getByUsername(username);
            user.validatePassword(password);
            return user;
        } catch (UserNotFoundException | InvalidPasswordException e) {
            throw new UserServiceException("Invalid username or password.", e);
        }
    }

    public void registerUser(String username, String email, String password, UserRole role)
            throws UserServiceException {
        try {
            userCreationValidator.validateUserCreation(username, email, password, role);
            User user = new User(username, email, password, role);
            userActivationService.beginActivation(user);
            userRepository.persist(user);
        } catch (UserRegistrationValidationException | UserAlreadyExistsException | UserActivationServiceException e) {
            throw new UserServiceException(e);
        }
    }

    public void updateUserEmail(User user, String email) throws UserActivationServiceException {
        user.updateEmail(email);
        userActivationService.beginActivation(user);
        userRepository.update(user);
    }

    public List<UserRole> getPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = Arrays.asList(UserRole.values());

        return userRoles.stream().filter(UserRole::isPubliclyRegistrable).collect(Collectors.toList());
    }

    public void updateUserCoordinate(String username, String address, String postCode, String city, String region,
            String country, String email) throws UserNotFoundException, UserActivationServiceException {
        User user = userRepository.getByUsername(username);
        user.setAddress(address);
        user.setPostCode(postCode);
        user.setCity(city);
        user.setRegion(region);
        user.setCountry(country);
        userRepository.update(user);
        if (!email.equals(user.getEmail())) {
            updateUserEmail(user, email);
        }

    }
}
