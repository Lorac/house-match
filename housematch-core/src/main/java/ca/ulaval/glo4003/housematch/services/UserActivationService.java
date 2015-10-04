package ca.ulaval.glo4003.housematch.services;

import java.util.UUID;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotActivatedException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.email.MailSender;

public class UserActivationService {

    private static final String ACTIVATION_BASE_URL = "http://localhost:8080/activation/";
    private static final String ACTIVATION_EMAIL_BODY = "Complete your HouseMatch registration by <a href=\"%s%d\">"
            + "activating your account</a>.";
    private static final String ACTIVATION_EMAIL_SUBJECT = "Activate your account";

    private UserRepository userRepository;
    private MailSender mailSender;

    public UserActivationService(final MailSender mailSender, final UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public void validateActivation(User user) throws UserActivationServiceException {
        try {
            user.validateActivation();
        } catch (UserNotActivatedException e) {
            throw new UserActivationServiceException(e);
        }
    }

    public void updateActivationEmail(User user, String email) throws UserActivationServiceException {
        user.setEmail(email);
        sendActivationMail(user);
    }

    public void beginActivation(User user) throws UserActivationServiceException {
        user.setActivationCode(generateActivationCode(user));
        sendActivationMail(user);
    }

    private UUID generateActivationCode(User user) {
        return UUID.randomUUID();
    }

    private void sendActivationMail(User user) throws UserActivationServiceException {
        try {
            mailSender.sendAsync(ACTIVATION_EMAIL_SUBJECT,
                    String.format(ACTIVATION_EMAIL_BODY, ACTIVATION_BASE_URL, user.getActivationCode()),
                    user.getEmail());
        } catch (MailSendException e) {
            throw new UserActivationServiceException(String.format(
                    "Could not send the activation mail. Please check that '%s' is a valid email address.",
                    user.getEmail()), e);
        }
    }

    public void completeActivation(UUID activationCode) throws UserActivationServiceException {
        User user;

        try {
            user = userRepository.getByActivationCode(activationCode);
        } catch (UserNotFoundException e) {
            throw new UserActivationServiceException(
                    String.format("Activation code '%s' is not valid.", activationCode), e);
        }

        user.activate();
    }

}
