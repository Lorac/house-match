package ca.ulaval.glo4003.housematch.spring.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
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
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
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
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertyDetailsFormViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertySearchResultsViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertyViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.AlertMessageViewModel;

public class PropertyControllerTest extends BaseControllerTest {

    private static final List<Property> SAMPLE_PROPERTY_LIST = new ArrayList<>();

    private Property propertyMock;
    private PropertyService propertyServiceMock;
    private UserService userServiceMock;
    private PropertyViewModelAssembler propertyViewModelAssemblerMock;
    private PropertyDetailsFormViewModelAssembler propertyDetailsFormViewModelAssemblerMock;
    private PropertySearchResultsViewModelAssembler propertySearchResultsViewModelAssemblerMock;
    private PropertyController propertyController;
    private String samplePropertyDetailsUpdateUrl;
    private PermissionEvaluator permissionEvaluatorMock;

    @Before
    public void init() throws Exception {
        super.init();
        initMocks();
        stubMethods();
        samplePropertyDetailsUpdateUrl = PropertyController.PROPERTY_DETAILS_UPDATE_BASE_URL + propertyMock.hashCode();
        propertyController = new PropertyController(propertyServiceMock, userServiceMock, propertyViewModelAssemblerMock,
                propertyDetailsFormViewModelAssemblerMock, propertySearchResultsViewModelAssemblerMock, permissionEvaluatorMock);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).setViewResolvers(viewResolver).build();
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyServiceMock = mock(PropertyService.class);
        userServiceMock = mock(UserService.class);
        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);
        propertyDetailsFormViewModelAssemblerMock = mock(PropertyDetailsFormViewModelAssembler.class);
        propertySearchResultsViewModelAssemblerMock = mock(PropertySearchResultsViewModelAssembler.class);
        permissionEvaluatorMock = mock(PermissionEvaluator.class);
    }

    private void stubMethods() throws Exception {
        when(propertyServiceMock.createProperty(any(), any(), any(), any())).thenReturn(propertyMock);
    }

    @Test
    public void propertyControllerRendersPropertyCreationView() throws Exception {
        ResultActions results = performGetRequest(PropertyController.PROPERTY_CREATION_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTY_CREATION_VIEW_NAME));
    }

    @Test
    public void propertyControllerRedirectsToPropertyDetailsUpdatePageUponSucessfulPropertyCreation() throws Exception {
        ResultActions results = postPropertyCreationForm();
        results.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl(samplePropertyDetailsUpdateUrl));
    }

    @Test
    public void propertyControllerCreatesPropertyDuringPropertyCreation() throws Exception {
        postPropertyCreationForm();
        verify(propertyServiceMock).createProperty(any(PropertyType.class), any(Address.class), any(BigDecimal.class), eq(userMock));
    }

    @Test
    public void propertyControllerRendersAlertMessageOnPropertyServiceExceptionDuringPropertyCreation() throws Exception {
        doThrow(new PropertyServiceException()).when(propertyServiceMock).createProperty(any(PropertyType.class), any(Address.class),
                any(BigDecimal.class), eq(userMock));

        ResultActions results = postPropertyCreationForm();

        results.andExpect(view().name(PropertyController.PROPERTY_CREATION_VIEW_NAME));
        results.andExpect(model().attribute(AlertMessageViewModel.NAME, hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void propertyControllerRendersPropertyDetailsUpdateView() throws Exception {
        ResultActions results = performGetRequest(samplePropertyDetailsUpdateUrl);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTY_DETAILS_UPDATE_VIEW_NAME));
    }

    @Test
    public void propertyControllerGetsPropertyUsingTheSpecifiedHashCodeDuringPropertyDetailsUpdateViewAccess() throws Exception {
        performGetRequest(samplePropertyDetailsUpdateUrl);
        verify(userServiceMock).getPropertyForSaleByHashCode(userMock, propertyMock.hashCode());
    }

    @Test
    public void propertyControllerAssemblesTheViewModelFromThePropertyDuringPropertyDetailsUpdateViewAccess() throws Exception {
        when(userServiceMock.getPropertyForSaleByHashCode(userMock, propertyMock.hashCode())).thenReturn(propertyMock);
        performGetRequest(samplePropertyDetailsUpdateUrl);
        verify(propertyDetailsFormViewModelAssemblerMock).assembleFromProperty(propertyMock);
    }

    @Test
    public void propertyControllerReturnsHttpStatusNotFoundOnInvalidHashCodeDuringPropertyDetailsUpdateViewAccess() throws Exception {
        doThrow(new PropertyNotFoundException()).when(userServiceMock).getPropertyForSaleByHashCode(userMock, propertyMock.hashCode());
        ResultActions results = performGetRequest(samplePropertyDetailsUpdateUrl);
        results.andExpect(status().isNotFound());
    }

    @Test
    public void propertyControllerRendersPropertyDetailsUpdateConfirmationViewUponSuccessfulPropertyDetailsUpdate() throws Exception {
        ResultActions results = postPropertyDetailsUpdateForm();

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME));
    }

    @Test
    public void propertyControllerUpdatesThePropertyDuringPropertyDetailsUpdate() throws Exception {
        when(userServiceMock.getPropertyForSaleByHashCode(userMock, propertyMock.hashCode())).thenReturn(propertyMock);
        postPropertyDetailsUpdateForm();
        verify(propertyServiceMock).updatePropertyDetails(eq(propertyMock), any(PropertyDetails.class));
    }

    @Test
    public void propertyControllerRendersAlertMessageOnPropertyServiceExceptionDuringPropertyDetailsUpdate() throws Exception {
        doThrow(new PropertyServiceException()).when(propertyServiceMock).updatePropertyDetails(any(Property.class),
                any(PropertyDetails.class));

        ResultActions results = postPropertyDetailsUpdateForm();

        results.andExpect(view().name(PropertyController.PROPERTY_DETAILS_UPDATE_VIEW_NAME));
        results.andExpect(model().attribute(AlertMessageViewModel.NAME, hasProperty("messageType", is(AlertMessageType.ERROR))));
    }

    @Test
    public void propertyControllerRendersSellerPropertyListView() throws Exception {
        ResultActions results = performGetRequest(PropertyController.PROPERTIES_FOR_SALE_LIST_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTIES_FOR_SALE_LIST_VIEW_NAME));
    }

    @Test
    public void propertyControllerRendersPropertySearchView() throws Exception {
        ResultActions results = performGetRequest(PropertyController.PROPERTY_SEARCH_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTY_SEARCH_VIEW_NAME));
    }

    @Test
    public void propertyControllerGetsAllPropertiesFromThePropertyServiceDuringPropertySearchRequest() throws Exception {
        performGetRequest(PropertyController.PROPERTY_SEARCH_EXECUTE_URL);
        verify(propertyServiceMock).getProperties();
    }

    @Test
    public void propertyControllerAssemblesTheViewModelFromThePropertiesDuringPropertySearchRequest() throws Exception {
        when(propertyServiceMock.getProperties()).thenReturn(SAMPLE_PROPERTY_LIST);
        performGetRequest(PropertyController.PROPERTY_SEARCH_EXECUTE_URL);
        verify(propertySearchResultsViewModelAssemblerMock).assembleFromPropertyList(SAMPLE_PROPERTY_LIST);
    }

    @Test
    public void propertyControllerRendersPropertySearchResultsViewUponCompletionOfPropertySearchRequest() throws Exception {
        ResultActions results = performGetRequest(PropertyController.PROPERTY_SEARCH_EXECUTE_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTY_SEARCH_VIEW_NAME));
    }

    @Test
    public void propertyControllerRendersPropertyView() throws Exception {
        ResultActions results = performPropertyGetRequest();

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTY_VIEW_NAME));
    }

    @Test
    public void propertyControllerGetsPropertyUsingTheSpecifiedHashCodeDuringPropertyViewAccess() throws Exception {
        performPropertyGetRequest();
        verify(propertyServiceMock).getPropertyByHashCode(propertyMock.hashCode());
    }

    @Test
    public void propertyControllerAssemblesTheViewModelFromThePropertyDuringPropertyViewAccess() throws Exception {
        when(propertyServiceMock.getPropertyByHashCode(propertyMock.hashCode())).thenReturn(propertyMock);
        performPropertyGetRequest();
        verify(propertyViewModelAssemblerMock).assembleFromProperty(propertyMock);
    }

    @Test
    public void propertyControllerReturnsHttpStatusNotFoundOnInvalidHashCodeDuringPropertyViewAccess() throws Exception {
        doThrow(new PropertyNotFoundException()).when(propertyServiceMock).getPropertyByHashCode(propertyMock.hashCode());
        ResultActions results = performPropertyGetRequest();
        results.andExpect(status().isNotFound());
    }

    @Test
    public void propertyControllerWillAskTheServiceToUpdateViewCountOnPropertyWhenVisited() throws Exception {
        when(propertyServiceMock.getPropertyByHashCode(propertyMock.hashCode())).thenReturn(propertyMock);
        performPropertyGetRequest();
        verify(propertyServiceMock).incrementViewCountOfProperty(propertyMock);
    }

    private ResultActions postPropertyCreationForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(PropertyController.PROPERTY_CREATION_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest = buildPropertyCreationFormParams(postRequest);

        return mockMvc.perform(postRequest);
    }

    private MockHttpServletRequestBuilder buildPropertyCreationFormParams(MockHttpServletRequestBuilder postRequest) {
        return postRequest.session(mockHttpSession);
    }

    private ResultActions postPropertyDetailsUpdateForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(samplePropertyDetailsUpdateUrl);
        postRequest.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest.session(mockHttpSession);

        return mockMvc.perform(postRequest);
    }

    private ResultActions performPropertyGetRequest() throws Exception {
        when(permissionEvaluatorMock.hasPermission(any(Authentication.class), anyObject(), anyObject())).thenReturn(true);
        return performGetRequest(PropertyController.PROPERTY_VIEW_BASE_URL + propertyMock.hashCode());
    }
}