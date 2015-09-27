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
import ca.ulaval.glo4003.housematch.email.EmailSender;
import ca.ulaval.glo4003.housematch.email.MailSendException;

public class UserService {

    private static final String ACTIVATION_BASE_URL = "http://localhost:8080/activation/";
    private static final String ACTIVATION_EMAIL_SUBJECT = "Activate your account";

    private EmailSender emailSender;
    private UserRepository userRepository;

    public UserService(final UserRepository userRepository, final EmailSender emailSender) {
        this.userRepository = userRepository;
        this.emailSender = emailSender;
    }

    public void validateUserLogin(String username, String password)
            throws UserNotFoundException, InvalidPasswordException, UserNotActivatedException {
        User user = getUserByUsername(username);
        user.isPasswordValid(password);
        user.validateActivation();
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.getByUsername(username);
    }

    public void createUser(String username, String email, String password, UserRole role)
            throws MailSendException, UserAlreadyExistsException {
        User user = new User(username, email, password, role);
        userRepository.persist(user);
        sendActivationLink(user);
    }

    private void sendActivationLink(User user) throws MailSendException {
        emailSender.send(ACTIVATION_EMAIL_SUBJECT,
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
