package ca.ulaval.glo4003.housematch.services;

import java.util.concurrent.ThreadLocalRandom;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotActivatedException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.email.MailSendException;
import ca.ulaval.glo4003.housematch.email.MailSender;

public class UserActivationService {

    private static final String ACTIVATION_BASE_URL = "http://localhost:8080/activation/";
    private static final String ACTIVATION_EMAIL_SUBJECT = "Activate your account";
    private static final Integer ACTIVATION_CODE_MIN_VALUE = 1;
    private static final Integer ACTIVATION_CODE_MAX_VALUE = Integer.MAX_VALUE;

    private UserRepository userRepository;
    private MailSender mailSender;

    public UserActivationService(final MailSender mailSender, final UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public void validateActivation(User user) throws UserNotActivatedException {
        user.validateActivation();
    }

    public void updateActivationEmail(User user, String email) {
        user.setEmail(email);
        sendActivationMail(user);
    }

    public void beginActivation(User user) {
        user.setActivationCode(generateActivationCode(user));
        sendActivationMail(user);
    }

    private int generateActivationCode(User user) {
        return ThreadLocalRandom.current().nextInt(ACTIVATION_CODE_MIN_VALUE, ACTIVATION_CODE_MAX_VALUE);
    }

    private void sendActivationMail(User user) throws MailSendException {
        mailSender.sendAsync(ACTIVATION_EMAIL_SUBJECT,
                String.format("Complete your HouseMatch registration by <a href=\"%s%d\">activating your account</a>.",
                        ACTIVATION_BASE_URL, user.getActivationCode()),
                user.getEmail());
    }

    public void completeActivation(Integer activationCode) throws UserNotFoundException {
        User user = userRepository.getByActivationCode(activationCode);
        user.activate();
    }

}
