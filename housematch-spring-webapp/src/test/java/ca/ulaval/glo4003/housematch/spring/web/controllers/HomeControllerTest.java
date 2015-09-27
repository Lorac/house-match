package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.spring.web.security.ResourceAccessValidator;

public class HomeControllerTest extends WebControllerTest {

    private HomeController homeController;
    private ResourceAccessValidator resourceAccessValidtorMock;

    @Before
    public void init() {
        super.init();
        resourceAccessValidtorMock = mock(ResourceAccessValidator.class);
        homeController = new HomeController(resourceAccessValidtorMock);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void loginControllerShouldRenderLoginView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name("home"));
    }
}