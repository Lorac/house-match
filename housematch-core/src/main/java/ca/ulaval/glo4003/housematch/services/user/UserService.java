package ca.ulaval.glo4003.housematch.services.user;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidator;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidator;

public class UserService {

    private UserRepository userRepository;
    private UserActivationService userActivationService;
    private UserRegistrationValidator userRegistrationValidator;
    private AddressValidator addressValidator;

    public UserService(final UserRepository userRepository, final UserRegistrationValidator userRegistrationValidator,
            final UserActivationService userActivationService, final AddressValidator addressValidator) {
        this.userRepository = userRepository;
        this.userRegistrationValidator = userRegistrationValidator;
        this.addressValidator = addressValidator;
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
            userRegistrationValidator.validateUserCreation(username, email, password, role);
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

    public void updateUserCoordinate(User user, Address address, String email)
            throws UserNotFoundException, UserActivationServiceException, AddressValidationException {
        addressValidator.validateAddress(address);
        user.setAddress(address);
        userRepository.update(user);
        if (!email.equals(user.getEmail())) {
            updateUserEmail(user, email);
        }

    }
}
