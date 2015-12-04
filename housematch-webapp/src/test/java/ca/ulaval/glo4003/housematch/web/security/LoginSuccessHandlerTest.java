package ca.ulaval.glo4003.housematch.web.security;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.web.controllers.BaseController;
import ca.ulaval.glo4003.housematch.web.controllers.HomeController;
import ca.ulaval.glo4003.housematch.web.controllers.RegistrationController;

public class LoginSuccessHandlerTest {

    private RedirectStrategy redirectStrategyMock;
    private HttpServletRequest httpServletRequestMock;
    private HttpServletResponse httpServletResponseMock;
    private HttpSession httpSessionMock;
    private User userMock;
    private Authentication authenticationMock;

    private LoginSuccessHandler loginSuccessHandler;

    @Before
    public void init() {
        initMocks();
        initStubs();
        loginSuccessHandler = new LoginSuccessHandler();
        loginSuccessHandler.setRedirectStrategy(redirectStrategyMock);
    }

    private void initMocks() {
        redirectStrategyMock = mock(RedirectStrategy.class);
        httpServletRequestMock = mock(HttpServletRequest.class);
        httpServletResponseMock = mock(HttpServletResponse.class);
        httpSessionMock = mock(HttpSession.class);
        userMock = mock(User.class);
        authenticationMock = mock(Authentication.class);
    }

    private void initStubs() {
        when(authenticationMock.getPrincipal()).thenReturn(userMock);
        when(httpServletRequestMock.getSession()).thenReturn(httpSessionMock);
    }

    @Test
    public void handlerDoesNotRedirectWhenHttpServletResponseIsCommitedOnLoginSuccess() throws Exception {
        when(httpServletResponseMock.isCommitted()).thenReturn(true);
        loginSuccessHandler.handle(httpServletRequestMock, httpServletResponseMock, authenticationMock);
        verify(redirectStrategyMock, never()).sendRedirect(eq(httpServletRequestMock), eq(httpServletResponseMock), anyString());
    }

    @Test
    public void userIsAddedToHttpSessionOnLoginSuccess() throws Exception {
        loginSuccessHandler.handle(httpServletRequestMock, httpServletResponseMock, authenticationMock);
        verify(httpSessionMock).setAttribute(BaseController.USER_ATTRIBUTE_NAME, userMock);
    }

    @Test
    public void handlerRedirectsToEmailReconfirmationUrlWhenUserIsNotActivated() throws Exception {
        when(userMock.isActivated()).thenReturn(false);
        loginSuccessHandler.handle(httpServletRequestMock, httpServletResponseMock, authenticationMock);
        verify(redirectStrategyMock).sendRedirect(httpServletRequestMock, httpServletResponseMock,
                RegistrationController.EMAIL_RECONFIRM_URL);
    }

    @Test
    public void handlerRedirectsToHomeUrlWhenUserIsActivated() throws Exception {
        when(userMock.isActivated()).thenReturn(true);
        loginSuccessHandler.handle(httpServletRequestMock, httpServletResponseMock, authenticationMock);
        verify(redirectStrategyMock).sendRedirect(httpServletRequestMock, httpServletResponseMock, HomeController.HOME_URL);
    }

    @Test
    public void settingRedirectStrategySetsTheSpecifiedRedirectStrategy() {
        loginSuccessHandler.setRedirectStrategy(redirectStrategyMock);
        assertSame(redirectStrategyMock, loginSuccessHandler.getRedirectStrategy());
    }

}
