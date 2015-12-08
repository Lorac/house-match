package ca.ulaval.glo4003.housematch.web.urlgenerators;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.user.UserActivationUriGenerator;
import ca.ulaval.glo4003.housematch.web.controllers.RegistrationController;

public class UserActivationUrlGenerator implements UserActivationUriGenerator {

    private static final String ACTIVATION_URL = "http://localhost:8080%s%s";

    @Override
    public String generateActivationUri(User user) {
        return String.format(ACTIVATION_URL, RegistrationController.ACTIVATION_BASE_URL, user.getActivationCode());
    }

}
