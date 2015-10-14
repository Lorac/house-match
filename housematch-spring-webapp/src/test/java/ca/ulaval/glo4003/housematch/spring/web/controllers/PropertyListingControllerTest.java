package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertyListingUpdateFormViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyListingCreationFormViewModel;

public class PropertyListingControllerTest extends MvcControllerTest {

    private static final String PROPERTY_TYPE_PARAMETER_NAME = "propertyType";
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.CONDO_LOFT;
    private static final String ADDRESS_PARAMETER_NAME = "address";
    private static final String SELLING_PRICE_PARAMETER_NAME = "propertyType";

    private Property propertyMock;
    private PropertyService propertyServiceMock;
    private UserService userServiceMock;
    private PropertyListingUpdateFormViewModelAssembler propertyListingUpdateFormViewModelAssemblerMock;
    private PropertyListingController propertyController;
    private String samplePropertyUpdateUrl;

    @Before
    public void init() throws Exception {
        super.init();
        initMocks();
        stubMethods();
        samplePropertyUpdateUrl = String.format(PropertyListingController.PROPERTY_LISTING_UPDATE_URL_FORMAT,
                propertyMock.hashCode());
        propertyController = new PropertyListingController(authorizationValidatorMock, propertyServiceMock,
                userServiceMock, propertyListingUpdateFormViewModelAssemblerMock);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).setViewResolvers(viewResolver).build();
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyServiceMock = mock(PropertyService.class);
        userServiceMock = mock(UserService.class);
        propertyListingUpdateFormViewModelAssemblerMock = mock(PropertyListingUpdateFormViewModelAssembler.class);
    }

    private void stubMethods() throws Exception {
        when(propertyServiceMock.createPropertyListing(eq(SAMPLE_PROPERTY_TYPE), any(Address.class),
                any(BigDecimal.class), eq(userMock))).thenReturn(propertyMock);
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
    public void propertyControllerCallsAuthorizationValidationServiceOnPropertyListingCreationViewAccess()
            throws Exception {
        performGetRequest(PropertyListingController.PROPERTY_LISTING_CREATION_URL);
        verify(authorizationValidatorMock).validateResourceAccess(
                PropertyListingController.PROPERTY_LISTING_CREATION_VIEW_NAME, mockHttpSession,
                PropertyListingController.USER_ATTRIBUTE_NAME);
    }

    @Test
    public void propertyControllerRedirectsToPropertyUpdatePageUponSucessfulListingCreation() throws Exception {
        ResultActions results = postPropertyListingCreationForm();
        results.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl(samplePropertyUpdateUrl));
    }

    @Test
    public void propertyControllerCreatesPropertyListingDuringPropertyListingCreation() throws Exception {
        postPropertyListingCreationForm();
        verify(propertyServiceMock).createPropertyListing(eq(SAMPLE_PROPERTY_TYPE), any(Address.class),
                any(BigDecimal.class), eq(userMock));
    }

    @Test
    public void propertyControllerCallsAuthorizationValidationServiceDuringPropertyListingCreation() throws Exception {
        postPropertyListingCreationForm();
        verify(authorizationValidatorMock).validateResourceAccess(
                PropertyListingController.PROPERTY_LISTING_CREATION_VIEW_NAME, mockHttpSession,
                HomeController.USER_ATTRIBUTE_NAME);
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
    public void propertyControllerRendersPropertyListingUpdateView() throws Exception {
        ResultActions results = performGetRequest(samplePropertyUpdateUrl);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyListingController.PROPERTY_LISTING_UPDATE_VIEW_NAME));
    }

    @Test
    public void propertyControllerCallsAuthorizationValidationServiceOnPropertyUpdateViewAccess() throws Exception {
        performGetRequest(samplePropertyUpdateUrl);
        verify(authorizationValidatorMock).validateResourceAccess(
                PropertyListingController.PROPERTY_LISTING_UPDATE_VIEW_NAME, mockHttpSession,
                PropertyListingController.USER_ATTRIBUTE_NAME);
    }

    @Test
    public void propertyControllerGetsPropertyListingUsingTheSpecifiedHashCodeDuringUpdateViewAccess()
            throws Exception {
        performGetRequest(samplePropertyUpdateUrl);
        verify(userServiceMock).getPropertyListingByHashCode(userMock, propertyMock.hashCode());
    }

    @Test
    public void propertyControllerAssemblesTheViewModelFromThePropertyDuringUpdateViewAccess() throws Exception {
        when(userServiceMock.getPropertyListingByHashCode(userMock, propertyMock.hashCode())).thenReturn(propertyMock);
        performGetRequest(samplePropertyUpdateUrl);
        verify(propertyListingUpdateFormViewModelAssemblerMock).assembleFromProperty(propertyMock);
    }

    @Test
    public void propertyControllerReturnsHttpStatusNotFoundOnInvalidHashCodeDuringUpdateViewAccess() throws Exception {
        doThrow(new PropertyNotFoundException()).when(userServiceMock).getPropertyListingByHashCode(userMock,
                propertyMock.hashCode());
        ResultActions results = performGetRequest(samplePropertyUpdateUrl);
        results.andExpect(status().isNotFound());
    }

    @Test
    public void propertyControllerCallsAuthorizationValidationServiceOnPropertyUpdate() throws Exception {
        postPropertyListingUpdateForm();
        verify(authorizationValidatorMock).validateResourceAccess(
                PropertyListingController.PROPERTY_LISTING_UPDATE_VIEW_NAME, mockHttpSession,
                PropertyListingController.USER_ATTRIBUTE_NAME);
    }

    @Test
    public void propertyControllerRendersPropertyUpdateConfirmationViewUponSuccessfulPropertyUpdate() throws Exception {
        ResultActions results = postPropertyListingUpdateForm();

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyListingController.PROPERTY_LISTING_CONFIRMATION_VIEW_NAME));
    }

    @Test
    public void propertyControllerUpdatesThePropertyDuringPropertyUpdate() throws Exception {
        when(userServiceMock.getPropertyListingByHashCode(userMock, propertyMock.hashCode())).thenReturn(propertyMock);
        postPropertyListingUpdateForm();
        verify(propertyServiceMock).updateProperty(eq(propertyMock), any(PropertyDetails.class));
    }

    @Test
    public void propertyControllerRendersAlertMessageOnPropertyServiceExceptionDuringPropertyUpdate() throws Exception {
        doThrow(new PropertyServiceException()).when(propertyServiceMock).updateProperty(any(Property.class),
                any(PropertyDetails.class));

        ResultActions results = postPropertyListingUpdateForm();

        results.andExpect(view().name(PropertyListingController.PROPERTY_LISTING_UPDATE_VIEW_NAME));
        results.andExpect(model().attribute(AlertMessageViewModel.VIEWMODEL_NAME,
                hasProperty("messageType", is(AlertMessageType.ERROR))));
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

    private ResultActions postPropertyListingUpdateForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(samplePropertyUpdateUrl);
        postRequest.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest.session(mockHttpSession);

        return mockMvc.perform(postRequest);
    }
}