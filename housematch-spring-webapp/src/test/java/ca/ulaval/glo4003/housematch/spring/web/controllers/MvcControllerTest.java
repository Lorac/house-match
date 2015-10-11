package ca.ulaval.glo4003.housematch.spring.web.controllers;

import ca.ulaval.glo4003.housematch.domain.user.User;
import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class MvcControllerTest {

    private static final String VIEW_NAME_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_NAME_SUFFIX = ".jsp";
    protected MockMvc mockMvc;
    protected InternalResourceViewResolver viewResolver;
    protected User userMock;
    protected MockHttpSession mockHttpSession;

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

    protected ResultActions performGetRequest(String url) throws Exception {
        MockHttpServletRequestBuilder getRequest = get(url).accept(MediaType.ALL).session(mockHttpSession);
        return mockMvc.perform(getRequest);
    }
}
