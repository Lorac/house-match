package ca.ulaval.glo4003.housematch.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import ca.ulaval.glo4003.housematch.domain.user.InvalidPasswordException;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.email.MailSender;
import ca.ulaval.glo4003.housematch.validators.UserRegistrationValidationException;
import ca.ulaval.glo4003.housematch.validators.UserRegistrationValidator;

public class UserService {

    private static final String MODIFICATION_BASE_URL = "http://localhost:8080/modifyProfile/";
    private static final String MODIFICATION_EMAIL_BODY = "Confirm your email modification by <a href=\"%s%s/%s\">"
            + "clicking this link</a>.";
    private static final String MODIFICATION_EMAIL_SUBJECT = "Confirm account modification";

    private UserRepository userRepository;
    private UserActivationService userActivationService;
    private UserRegistrationValidator userCreationValidator;
    private MailSender mailSender;

    public UserService(final UserRepository userRepository, final UserRegistrationValidator userCreationValidator,
            final UserActivationService userActivationService, final MailSender mailSender) {
        this.userRepository = userRepository;
        this.userCreationValidator = userCreationValidator;
        this.userActivationService = userActivationService;
        this.mailSender = mailSender;

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
            userRepository.persist(user);
            userActivationService.beginActivation(user);
        } catch (UserRegistrationValidationException | UserAlreadyExistsException | UserActivationServiceException e) {
            throw new UserServiceException(e);
        }
    }

    public List<UserRole> getPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = Arrays.asList(UserRole.values());

        return userRoles.stream().filter(UserRole::isPubliclyRegistrable).collect(Collectors.toList());
    }

    public void updateUserCoordinate(String username, String address, String postCode, String city, String region,
            String country, String email) throws UserNotFoundException, UserMailModificationException {
        User user = userRepository.getByUsername(username);
        user.setAddress(address);
        user.setPostCode(postCode);
        user.setCity(city);
        user.setRegion(region);
        user.setCountry(country);
        if (!email.equals(user.getEmail())) {
            user.setTemporaryEmail(email);
            sendConfirmationMail(user, email);
        }
    }

    public void completeEmailModification(Integer code, String username) throws UserNotFoundException {
        User user = userRepository.getByUsername(username);
        user.setEmail(user.getTemporaryEmail());
        user.setTemporaryEmail("");
        user.endModification();
    }

    private void sendConfirmationMail(User user, String email) throws UserMailModificationException {
        UUID code = UUID.randomUUID();
        user.startModification(code);
        try {
            mailSender.sendAsync(MODIFICATION_EMAIL_SUBJECT,
                    String.format(MODIFICATION_EMAIL_BODY, MODIFICATION_BASE_URL, user.getUsername(), code), email);
        } catch (MailSendException e) {
            throw new UserMailModificationException(String.format(
                    "Could not send the mail. Please check that '%s' is a valid email address.", user.getEmail()), e);
        }
    }
}
