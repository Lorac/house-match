package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.anyString;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.DomainException;
import ca.ulaval.glo4003.housematch.domain.user.InvalidRoleException;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.UserService;

public class UserControllerTest extends ControllerTest {
    
    private UserService userServiceMock;
    private UserController userController;
    private static final String SAMPLE_STRING = "SAMPLE";
    private UserRole role;
    
    private MockHttpSession mockSession;

    @Before
    public void init() {
        super.init();
        userServiceMock = mock(UserService.class);
        userController = new UserController(userServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setViewResolvers(viewResolver).build();
        mockSession = new MockHttpSession();
    }

    @Test
    public void loginControllerShouldRenderLoginView() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/login").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name("login"));
    }

    @Test
    public void loginControllerShouldRenderLoginViewWithUsernameAndPassword() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/login").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(model().attribute("loginForm", hasProperty("username")));
        results.andExpect(model().attribute("loginForm", hasProperty("password")));

    }
    
    @Test
    public void logoutControllerRendersLoginPageUponLogout() throws Exception {
    	MockHttpServletRequestBuilder getRequest = get("/logout").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest);
        
        results.andExpect(view().name("login"));
        results.andExpect(status().isOk());
    }
    
    @Test
    public void accessRefusedToRestrictedPagesToUserWithWrongRole() throws Exception {
        doThrow(new InvalidRoleException("")).when(userServiceMock).validateRole(anyString(), any(UserRole.class));
        mockSession.setAttribute("username", SAMPLE_STRING);

        MockHttpServletRequestBuilder getRequest = get("/seller").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest.session(mockSession));
        
        results.andExpect(view().name("error"));
        results.andExpect(status().isForbidden());
    }

    @Test
    public void accessGrantedToRestrictedPagesToUserWithRightRole() throws Exception {
        doNothing().when(userServiceMock).validateRole(SAMPLE_STRING, role.SELLER);
        mockSession.setAttribute("username", SAMPLE_STRING);
        
        MockHttpServletRequestBuilder getRequest = get("/seller").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest.session(mockSession));
        
        results.andExpect(view().name("sellerPage"));
        results.andExpect(status().isOk());
    }

    @Test
    public void accessDeniedToRestrictedPagesToAnonynmousUsers() throws Exception {
        doThrow(new UserNotFoundException("")).when(userServiceMock).validateRole(anyString(), any(UserRole.class));
        
        MockHttpServletRequestBuilder getRequest = get("/buyer").accept(MediaType.ALL);
        ResultActions results = mockMvc.perform(getRequest.session(mockSession));
        
        results.andExpect(view().name("error"));
        results.andExpect(status().isForbidden());
    }
}
