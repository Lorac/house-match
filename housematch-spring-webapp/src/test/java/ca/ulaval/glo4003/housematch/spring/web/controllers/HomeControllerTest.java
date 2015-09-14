package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class HomeControllerTest extends ControllerTest {

    @InjectMocks
    private HomeController homeControllerTest;

    @Before
    public void init() {
        super.init();
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeControllerTest).setViewResolvers(viewResolver).build();
    }

    @Test
    public void loginControllerShouldRenderLoginView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name("home"));
    }
}