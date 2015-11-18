package ca.ulaval.glo4003.housematch.spring.web.controllers;

import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertyViewModelAssembler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeControllerTest extends BaseControllerTest {

    private static final int TOP_FIVE = 5;
    private static final String PROPERTIES_ATTRIBUTE_NAME = "properties";
    private HomeController homeController;
    private PropertyService propertyService;
    private PropertyViewModelAssembler propertyViewModelAssemblerMock;

    @Before
    public void init() throws Exception {
        super.init();
        propertyService = mock(PropertyService.class);
        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);
        homeController = new HomeController(propertyService, propertyViewModelAssemblerMock);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).setViewResolvers(viewResolver).build();
        when(propertyService.getMostViewedPropertiesForCategory(TOP_FIVE, PropertyType.valueOf("COMMERCIAL"))).thenReturn(new LinkedList<>());
    }

    @Test
    public void homeControllerRendersHomeViewForAnonymousUser() throws Exception {
        mockHttpSession.removeAttribute(HomeController.USER_ATTRIBUTE_NAME);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(HomeController.HOME_VIEW_NAME));
    }

    @Test
    public void homeControllerRedirectsToAdminHomeViewWhenUserRoleIsAdmin() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.ADMINISTRATOR);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(HomeController.ADMIN_HOME_URL));
    }

    @Test
    public void homeControllerRedirectsToSellerHomeViewWhenUserRoleIsSeller() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.SELLER);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(HomeController.SELLER_HOME_URL));
    }

    @Test
    public void homeControllerRedirectsToBuyerHomeViewWhenUserRoleIsBuyer() throws Exception {
        when(userMock.getRole()).thenReturn(UserRole.BUYER);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().is3xxRedirection());
        results.andExpect(redirectedUrl(HomeController.BUYER_HOME_URL));
    }

    @Test
    @Ignore //TODO: Doit être retravailler pour accepter les queryParams.
    public void homeControllerWillRendersMostPopularPropertiesWhenAnonymousVisitThePage() throws Exception {
        mockHttpSession.removeAttribute(HomeController.USER_ATTRIBUTE_NAME);

        ResultActions results = performGetRequest(HomeController.HOME_URL);

        results.andExpect(status().isOk());
        results.andExpect(model().attribute(PROPERTIES_ATTRIBUTE_NAME, hasSize(0)));
    }
}