package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ca.ulaval.glo4003.housematch.domain.user.User;

public class MvcControllerTest {

    protected MockMvc mockMvc;
    protected InternalResourceViewResolver viewResolver;
    protected User userMock;
    protected MockHttpSession mockHttpSession;

    private static final String VIEW_NAME_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_NAME_SUFFIX = ".jsp";

    @Before
    public void init() {
        initViewResolver();

        userMock = mock(User.class);

        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(HomeController.USER_ATTRIBUTE_NAME, userMock);
    }

    private void initViewResolver() {
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(VIEW_NAME_PREFIX);
        viewResolver.setSuffix(VIEW_NAME_SUFFIX);
    }
}
