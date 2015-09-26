package ca.ulaval.glo4003.housematch.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.email.CannotSendEmailException;
import ca.ulaval.glo4003.housematch.email.EmailSender;

public class UserService {

    private static final String ACTIVATE_SUBJECT = "Activate your account";
    private EmailSender emailSender;
    private UserRepository userRepository;

    public UserService(final UserRepository userRepository, final EmailSender emailSender) {
        this.userRepository = userRepository;
        this.emailSender = emailSender;
    }

    public void validateUser(String username, String password) {
        User user = getUserByUsername(username);
        user.validatePassword(password);
        user.validateActivation();
    }

    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public void createUser(String username, String email, String password, UserRole role)
            throws CannotSendEmailException {
        User user = new User(username, email, password, role);
        userRepository.persist(user);
        sendActivationLink(user);
    }

    private void sendActivationLink(User user) throws CannotSendEmailException {
        emailSender.send(ACTIVATE_SUBJECT,
                String.format(
                        "Click on this link : <a href=\"http://localhost:8080/activation/%d\">activation link</a>",
                        user.hashCode()),
                user.getEmail());
    }

    public List<UserRole> getPubliclyRegistrableUserRoles() {
        List<UserRole> userRoles = Arrays.asList(UserRole.values());

        return userRoles.stream().filter(UserRole::isPubliclyRegistrable).collect(Collectors.toList());
    }

    public void activateUser(int hash) {
        User user = userRepository.getByHash(hash);
        user.setActivation(true);
    }
}
