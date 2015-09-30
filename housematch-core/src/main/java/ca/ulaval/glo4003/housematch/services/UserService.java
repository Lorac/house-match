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
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.email.MailSender;
import ca.ulaval.glo4003.housematch.validators.UserCreationValidationException;
import ca.ulaval.glo4003.housematch.validators.UserCreationValidator;

public class UserService {

    private static final String ACTIVATION_BASE_URL = "http://localhost:8080/activation/";
    private static final String ACTIVATION_EMAIL_SUBJECT = "Activate your account";

    private MailSender mailSender;
    private UserRepository userRepository;
    private UserCreationValidator userCreationValidator;

    public UserService(final UserRepository userRepository, final UserCreationValidator userCreationValidator,
            final MailSender mailSender) {
        this.userRepository = userRepository;
        this.userCreationValidator = userCreationValidator;
        this.mailSender = mailSender;
    }

    public User getUserByLoginCredentials(String username, String password)
            throws UserNotFoundException, InvalidPasswordException, UserNotActivatedException {
        User user = getUserByUsername(username);
        user.validatePassword(password);
        return user;
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.getByUsername(username);
    }

    public void createUser(String username, String email, String password, UserRole role)
            throws MailSendException, UserAlreadyExistsException, UserCreationValidationException {
        userCreationValidator.validateUserCreation(username, email, password, role);

        User user = new User(username, email, password, role);
        userRepository.persist(user);
        sendActivationLink(user);
    }

    public void updateActivationEmail(User user, String email) {
        user.setEmail(email);
        sendActivationLink(user);
    }

    private void sendActivationLink(User user) throws MailSendException {
        mailSender.send(ACTIVATION_EMAIL_SUBJECT,
                String.format("Complete your HouseMatch registration by <a href=\"%s%d\">activating your account</a>.",
                        ACTIVATION_BASE_URL, user.hashCode()),
                user.getEmail());
    }

    public List<UserRole> getPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = Arrays.asList(UserRole.values());

        return userRoles.stream().filter(UserRole::isPubliclyRegistrable).collect(Collectors.toList());
    }

    public void activateUser(int hashCode) throws UserNotFoundException {
        User user = userRepository.getByHashCode(hashCode);
        user.activate();
    }
}
