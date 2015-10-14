package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.spring.web.security.AuthorizationValidator;

public class BaseControllerTest {

    protected MockMvc mockMvc;
    protected InternalResourceViewResolver viewResolver;
    protected AuthorizationValidator authorizationValidatorMock;
    protected User userMock;
    protected MockHttpSession mockHttpSession;

    private static final String VIEW_NAME_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_NAME_SUFFIX = ".jsp";

    @Before
    public void init() throws Exception {
        initViewResolver();

        authorizationValidatorMock = mock(AuthorizationValidator.class);
        userMock = mock(User.class);

        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(HomeController.USER_ATTRIBUTE_NAME, userMock);
    }

    private void initViewResolver() {
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(VIEW_NAME_PREFIX);
        viewResolver.setSuffix(VIEW_NAME_SUFFIX);
    }

    protected ResultActions performGetRequest(String url) throws Exception {
        MockHttpServletRequestBuilder getRequest = get(url).accept(MediaType.ALL).session(mockHttpSession);
        return mockMvc.perform(getRequest);
    }
}
