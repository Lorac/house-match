package ca.ulaval.glo4003.housematch.services.user;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.EmailValidator;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserFactory;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidator;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidator;

public class UserService {

    private UserFactory userFactory;
    private UserRepository userRepository;
    private UserActivationService userActivationService;
    private UserRegistrationValidator userRegistrationValidator;
    private AddressValidator addressValidator;

    public UserService(final UserFactory userFactory, final UserRepository userRepository,
            final UserRegistrationValidator userRegistrationValidator, final UserActivationService userActivationService,
            final AddressValidator addressValidator) {
        this.userFactory = userFactory;
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

    public void registerUser(String username, String email, String password, UserRole role) throws UserServiceException {
        try {
            userRegistrationValidator.validateUserCreation(username, email, password, role);
            User user = userFactory.createUser(username, email, password, role);
            userActivationService.beginActivation(user);
            userRepository.persist(user);
        } catch (UserRegistrationValidationException | UserAlreadyExistsException | UserActivationServiceException e) {
            throw new UserServiceException(e);
        }
    }

    public Property getPropertyByHashCode(User user, int propertyHashCode) throws PropertyNotFoundException {
        return user.getPropertyByHashCode(propertyHashCode);
    }

    public void updateUserEmail(User user, String email) throws UserActivationServiceException, UserServiceException {
        if (email.equals(user.getEmail())) {
            return;
        } else if (!EmailValidator.getInstance().isValid(email)) {
            throw new UserServiceException("The email format is not valid.");
        }
        user.updateEmail(email);
        userActivationService.beginActivation(user);
    }

    public List<UserRole> getPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = Arrays.asList(UserRole.values());
        return userRoles.stream().filter(UserRole::isPubliclyRegistrable).collect(Collectors.toList());
    }

    public void updateUserContactInformation(User user, Address address, String email) throws UserServiceException {
        try {
            addressValidator.validateAddress(address);
            user.setAddress(address);
            updateUserEmail(user, email);
            userRepository.update(user);
        } catch (UserActivationServiceException | AddressValidationException e) {
            throw new UserServiceException(e.getMessage(), e);
        }
    }
}
