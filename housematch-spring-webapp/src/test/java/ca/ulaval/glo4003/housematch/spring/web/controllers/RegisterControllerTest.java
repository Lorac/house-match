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

import ca.ulaval.glo4003.housematch.services.UserService;

public class RegisterControllerTest extends ControllerTest {

    private UserService userServiceMock;
    private RegisterController registerController;

    @Before
    public void init() {
        super.init();
        userServiceMock = mock(UserService.class);
        registerController = new RegisterController(userServiceMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(registerController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void registerControllerShouldRenderRegisterView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/register").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name("register"));
    }

}
