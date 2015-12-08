package ca.ulaval.glo4003.housematch.web.urlgenerators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.user.User;

public class UserActivationUrlGeneratorTest {

    private User userMock;
    private UserActivationUrlGenerator userActivationUriGenerator;

    @Before
    public void init() {
        userMock = mock(User.class);
        userActivationUriGenerator = new UserActivationUrlGenerator();
    }

    @Test
    public void generatingTheActivationUrlGetsTheActivationCodeFromTheUser() {
        userActivationUriGenerator.generateActivationUri(userMock);
        verify(userMock).getActivationCode();
    }
}
