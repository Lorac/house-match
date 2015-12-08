package ca.ulaval.glo4003.housematch.services.user;

import java.util.UUID;

import org.apache.commons.validator.routines.EmailValidator;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.email.MailSender;

public class UserActivationService {

    private static final String ACTIVATION_EMAIL_BODY = "Complete your HouseMatch registration by <a href=\"%s\">"
            + "activating your account</a>.";
    private static final String ACTIVATION_EMAIL_SUBJECT = "Activate your account";

    private UserRepository userRepository;
    private UserActivationUriGenerator userActivationUriGenerator;
    private MailSender mailSender;

    public UserActivationService(final MailSender mailSender, final UserRepository userRepository,
            final UserActivationUriGenerator userActivationUriGenerator) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.userActivationUriGenerator = userActivationUriGenerator;
    }

    public void beginActivation(User user) throws UserActivationServiceException {
        user.setActivationCode(UUID.randomUUID());
        sendActivationMail(user);
    }

    private void sendActivationMail(User user) throws UserActivationServiceException {
        try {
            String activationUri = userActivationUriGenerator.generateActivationUri(user);
            mailSender.sendAsync(ACTIVATION_EMAIL_SUBJECT, String.format(ACTIVATION_EMAIL_BODY, activationUri), user.getEmail());
        } catch (MailSendException e) {
            throw new UserActivationServiceException(
                    String.format("Could not send the activation mail. Make sure '%s' is a valid email address.", user.getEmail()), e);
        }
    }

    public void resendActivationMail(User user, String email) throws UserActivationServiceException {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new UserActivationServiceException("The email format is not valid.");
        }
        user.updateEmail(email);
        beginActivation(user);
    }

    public void completeActivation(UUID activationCode) throws UserActivationServiceException {
        try {
            User user = userRepository.getByActivationCode(activationCode);
            user.activate();
            userRepository.update(user);
        } catch (UserNotFoundException e) {
            throw new UserActivationServiceException(String.format("Activation code '%s' is not valid.", activationCode), e);
        }
    }

}
