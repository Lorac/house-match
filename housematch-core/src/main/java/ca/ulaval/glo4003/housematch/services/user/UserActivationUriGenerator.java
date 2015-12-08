package ca.ulaval.glo4003.housematch.services.user;

import ca.ulaval.glo4003.housematch.domain.user.User;

public interface UserActivationUriGenerator {

    String generateActivationUri(User user);

}
