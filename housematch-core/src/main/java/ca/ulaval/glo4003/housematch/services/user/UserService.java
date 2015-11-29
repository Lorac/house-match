package ca.ulaval.glo4003.housematch.services.user;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
import ca.ulaval.glo4003.housematch.statistics.user.UserStatistics;
import ca.ulaval.glo4003.housematch.statistics.user.UserStatisticsCollector;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidator;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.user.UserRegistrationValidator;

public class UserService {

    private UserFactory userFactory;
    private UserRepository userRepository;
    private UserActivationService userActivationService;
    private UserStatisticsCollector userStatisticsCollector;
    private UserRegistrationValidator userRegistrationValidator;
    private AddressValidator addressValidator;

    public UserService(final UserFactory userFactory, final UserRepository userRepository,
            final UserActivationService userActivationService, final UserStatisticsCollector userStatisticCollector,
            final UserRegistrationValidator userRegistrationValidator, final AddressValidator addressValidator) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
        this.userActivationService = userActivationService;
        this.userStatisticsCollector = userStatisticCollector;
        this.userRegistrationValidator = userRegistrationValidator;
        this.addressValidator = addressValidator;
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
            userRegistrationValidator.validateUserRegistration(username, email, password, role);
            User user = userFactory.createUser(username, email, password, role);
            userRepository.persist(user);
            userActivationService.beginActivation(user);
        } catch (UserRegistrationValidationException | UserAlreadyExistsException | UserActivationServiceException e) {
            throw new UserServiceException(e);
        }
    }

    public Property getPropertyForSaleByHashCode(User user, int propertyHashCode) throws PropertyNotFoundException {
        return user.getPropertyForSaleByHashCode(propertyHashCode);
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
            throw new UserServiceException(e);
        }
    }

    private void updateUserEmail(User user, String email) throws UserActivationServiceException, UserServiceException {
        if (email.equals(user.getEmail())) {
            return;
        } else if (!EmailValidator.getInstance().isValid(email)) {
            throw new UserServiceException("The email format is not valid.");
        }
        user.updateEmail(email);
        userActivationService.beginActivation(user);
    }

    public UserStatistics getStatistics() {
        return userStatisticsCollector.getStatistics();
    }

    public void applyUserStatusPolicy() {
        List<User> users = userRepository.getAll();
        users.stream().forEach(User::applyUserStatusPolicy);
    }

    public void addFavoritePropertyToUser(User user, Property property) {
        user.addPropertyToFavorites(property);
        userRepository.update(user);
    }

    public Set<Property> getFavoriteProperties(User user) {
        return user.getFavoriteProperties();
    }
}
