package ca.ulaval.glo4003.housematch.spring.web.controllers;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@WebAppConfiguration
@ContextConfiguration("classpath:housematch-servlet-test-context.xml")
public class ControllerTest {

    protected MockMvc mockMvc;
    protected InternalResourceViewResolver viewResolver;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
    }
}
