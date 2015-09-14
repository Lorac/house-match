package ca.ulaval.glo4003.housematch.spring.web.controllers;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class ControllerTest {

    protected MockMvc mockMvc;
    protected InternalResourceViewResolver viewResolver;

    private static final String VIEW_NAME_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_NAME_SUFFIX = ".jsp";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(VIEW_NAME_PREFIX);
        viewResolver.setSuffix(VIEW_NAME_SUFFIX);
    }
}
