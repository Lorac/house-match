package ca.ulaval.glo4003.housematch.spring.web.controllers;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyListingDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListingCreationFormViewModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PropertyListingControllerTest extends MvcControllerTest {

    private static final String PROPERTY_TYPE_PARAMETER_NAME = "propertyType";
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.CONDO_LOFT;
    private static final String ADDRESS_PARAMETER_NAME = "address";
    private static final String SELLING_PRICE_PARAMETER_NAME = "propertyType";
    private static final String SAMPLE_PROPERT_ID = "0";
    private static final String SAMPLE_UPDATE_URL = "/updateListing/0";

    private PropertyService propertyServiceMock;
    private PropertyListingController propertyController;

    @Before
    public void init() {
        super.init();
        propertyServiceMock = mock(PropertyService.class);
        propertyController = new PropertyListingController(propertyServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void propertyControllerRendersPropertyListingCreationView() throws Exception {
        ResultActions results = performGetRequest(PropertyListingController.PROPERTY_LISTING_CREATION_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyListingController.PROPERTY_LISTING_CREATION_VIEW_NAME));
    }

    @Test
    public void propertyControllerRendersPropertyListingCreationViewWithTheCorrectFields() throws Exception {
        ResultActions results = performGetRequest(PropertyListingController.PROPERTY_LISTING_CREATION_URL);

        results.andExpect(model().attribute(PropertyListingCreationFormViewModel.VIEWMODEL_NAME,
                hasProperty(PROPERTY_TYPE_PARAMETER_NAME)));
        results.andExpect(model().attribute(PropertyListingCreationFormViewModel.VIEWMODEL_NAME,
                hasProperty(ADDRESS_PARAMETER_NAME)));
        results.andExpect(model().attribute(PropertyListingCreationFormViewModel.VIEWMODEL_NAME,
                hasProperty(SELLING_PRICE_PARAMETER_NAME)));
    }

    @Test
    public void propertyControllerRedirectsToPropertyUpdatePageWhenCreationSucessful() throws Exception {
        ResultActions results = postPropertyListingCreationForm();
        results.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl(SAMPLE_UPDATE_URL));
    }

    @Test
    public void propertyControllerCreatesPropertyListingDuringPropertyListingCreation() throws Exception {
        postPropertyListingCreationForm();
        verify(propertyServiceMock).createPropertyListing(eq(SAMPLE_PROPERTY_TYPE), any(Address.class),
                any(BigDecimal.class), eq(userMock));
    }

    @Test
    public void propertyControllerRendersAlertMessageOnPropertyServiceExceptionDuringPropertyListingCreation()
            throws Exception {
        doThrow(new PropertyServiceException()).when(propertyServiceMock).createPropertyListing(
                eq(SAMPLE_PROPERTY_TYPE), any(Address.class), any(BigDecimal.class), eq(userMock));

        ResultActions results = postPropertyListingCreationForm();

        results.andExpect(view().name(PropertyListingController.PROPERTY_LISTING_CREATION_VIEW_NAME));
        results.andExpect(model().attribute(AlertMessageViewModel.VIEWMODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void whenGivenInvalidHashUpdatePageDisplaysErrorMessage() throws Exception {
        doThrow(new PropertyServiceException()).when(propertyServiceMock).propertyBelongsToSeller(any(int.class), any(User.class));
        getPropertyListingUpdateForm().andExpect(view().name(PropertyListingController.RESOURCE_NOT_FOUND_VIEW_NAME));
    }

    @Test
    public void whenGivenValidHashUpdatePageRendersSuccessfully() throws Exception {
        when(propertyServiceMock.propertyBelongsToSeller(any(int.class), any(User.class))).thenReturn(true);
        getPropertyListingUpdateForm()
                .andExpect(view().name(PropertyListingController.PROPERTY_LISTING_UDPATE_VIEW_NAME));
    }

    @Test
    public void whenDetailsUpdateMadeOnInvalidHashDisplayErrorMessage() throws Exception {
        doThrow(new PropertyServiceException()).when(propertyServiceMock).updateProperty(any(int.class),
                any(PropertyListingDetails.class), any(User.class));
        ;
        postPropertyListingUpdateForm()
                .andExpect(view().name(PropertyListingController.PROPERTY_LISTING_UDPATE_VIEW_NAME));
    }

    @Test
    public void whenDetailsUpdateMadeOnValidHashDisplayRedirectToConfirmation() throws Exception {
        postPropertyListingUpdateForm()
                .andExpect(view().name(PropertyListingController.PROPERTY_LISTING_CONFIRMATION_VIEW_NAME));
    }

    @Test
    public void whenValidDetailsUpdateGivenRedirectToConfirmation() throws Exception {
        postPropertyListingUpdateForm()
                .andExpect(view().name(PropertyListingController.PROPERTY_LISTING_CONFIRMATION_VIEW_NAME));
    }

    @Test
    public void buyerCanEditPropertyThatBelongsToHim() throws Exception {
        when(propertyServiceMock.propertyBelongsToSeller(any(int.class), any(User.class))).thenReturn(true);
        getPropertyListingUpdateForm()
                .andExpect(view().name(PropertyListingController.PROPERTY_LISTING_UDPATE_VIEW_NAME));
    }

    @Test
    public void buyerCannotEditPropertyThatDoesNotBelongToHim() throws Exception {
        when(propertyServiceMock.propertyBelongsToSeller(any(int.class), any(User.class))).thenReturn(false);
        getPropertyListingUpdateForm()
                .andExpect(view().name(PropertyListingController.RESOURCE_FORBIDDEN_VIEW_NAME));
    }

    private ResultActions postPropertyListingUpdateForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(SAMPLE_UPDATE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildPropertyListingUpdateFormParams(postRequest);

        return mockMvc.perform(postRequest);
    }

    private ResultActions getPropertyListingUpdateForm() throws Exception {
        MockHttpServletRequestBuilder getRequest = get(SAMPLE_UPDATE_URL);
        getRequest = buildPropertyListingUpdateFormParams(getRequest);

        return mockMvc.perform(getRequest);
    }

    private MockHttpServletRequestBuilder buildPropertyListingUpdateFormParams(
            MockHttpServletRequestBuilder postRequest) {
        return postRequest.session(mockHttpSession);
    }

    private ResultActions postPropertyListingCreationForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(PropertyListingController.PROPERTY_LISTING_CREATION_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildPropertyListingCreationFormParams(postRequest);

        return mockMvc.perform(postRequest);
    }

    private MockHttpServletRequestBuilder buildPropertyListingCreationFormParams(
            MockHttpServletRequestBuilder postRequest) {
        return postRequest.param(PROPERTY_TYPE_PARAMETER_NAME, SAMPLE_PROPERTY_TYPE.toString())
                .session(mockHttpSession);
    }
}
