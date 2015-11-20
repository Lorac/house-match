package ca.ulaval.glo4003.housematch.web.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.web.controllers.HomeController;

public class BaseControllerTest {

    private static final String VIEW_NAME_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_NAME_SUFFIX = ".jsp";
    protected MockMvc mockMvc;
    protected InternalResourceViewResolver viewResolver;
    protected User userMock;
    protected MockHttpSession mockHttpSession;
    private Authentication authenticationMock;

    @Before
    public void init() throws Exception {
        initViewResolver();
        initMocks();
        initHttpSessionMock();
    }

    private void initViewResolver() {
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(VIEW_NAME_PREFIX);
        viewResolver.setSuffix(VIEW_NAME_SUFFIX);
    }

    private void initMocks() {
        userMock = mock(User.class);
        authenticationMock = mock(Authentication.class);
        when(authenticationMock.getPrincipal()).thenReturn(userMock);
    }

    private void initHttpSessionMock() {
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(HomeController.USER_ATTRIBUTE_NAME, userMock);
    }

    protected ResultActions performGetRequest(String url) throws Exception {
        MockHttpServletRequestBuilder getRequest = buildDefaultGetRequest(url);
        return mockMvc.perform(getRequest);
    }

    protected MockHttpServletRequestBuilder buildDefaultGetRequest(String url) {
        return get(url).accept(MediaType.ALL).session(mockHttpSession).principal(authenticationMock);
    }
}
