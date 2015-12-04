package ca.ulaval.glo4003.housematch.web.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ca.ulaval.glo4003.housematch.domain.SortOrder;
import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertySortColumn;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.web.assemblers.PropertyListViewModelAssembler;
import ca.ulaval.glo4003.housematch.web.assemblers.PropertyViewModelAssembler;
import ca.ulaval.glo4003.housematch.web.viewmodels.AlertMessageType;
import ca.ulaval.glo4003.housematch.web.viewmodels.AlertMessageViewModel;

public class PropertyControllerTest extends BaseControllerTest {

    private static final List<Property> SAMPLE_PROPERTY_LIST = new ArrayList<>();
    private static final Set<Property> SAMPLE_PROPERTY_SET = new HashSet<>();
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.CONDO_LOFT;
    private static final PropertySortColumn SAMPLE_PROPERTY_SORT_COLUMN = PropertySortColumn.VIEW_COUNT;
    private static final SortOrder SAMPLE_SORT_ORDER = SortOrder.ASCENDING;

    private static final String PROPERTY_TYPE_REQUEST_PARAMETER_NAME = "propertyType";
    private static final String PROPERTY_SORT_COLUMN_PARAMETER_NAME = "sortColumn";
    private static final String PROPERTY_SORT_ORDER_PARAMETER_NAME = "sortOrder";

    private Property propertyMock;
    private PropertyService propertyServiceMock;
    private UserService userServiceMock;
    private PropertyViewModelAssembler propertyViewModelAssemblerMock;
    private PropertyListViewModelAssembler propertyListViewModelAssemblerMock;
    private PropertyController propertyController;
    private String samplePropertyUpdateUrl;

    @Before
    public void init() throws Exception {
        super.init();
        initMocks();
        initStubs();
        samplePropertyUpdateUrl = PropertyController.PROPERTY_UPDATE_BASE_URL + propertyMock.hashCode();
        propertyController = new PropertyController(propertyServiceMock, userServiceMock, propertyViewModelAssemblerMock,
                propertyListViewModelAssemblerMock);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).setViewResolvers(viewResolver).build();
    }

    private void initMocks() {
        propertyMock = mock(Property.class);
        propertyServiceMock = mock(PropertyService.class);
        userServiceMock = mock(UserService.class);
        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);
        propertyListViewModelAssemblerMock = mock(PropertyListViewModelAssembler.class);
    }

    private void initStubs() throws Exception {
        when(propertyServiceMock.createProperty(any(), any(), any(), any())).thenReturn(propertyMock);
    }

    @Test
    public void propertyControllerRendersPropertyCreationView() throws Exception {
        ResultActions results = performGetRequest(PropertyController.PROPERTY_CREATION_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTY_CREATION_VIEW_NAME));
    }

    @Test
    public void propertyControllerRedirectsToPropertyUpdatePageUponSucessfulPropertyCreation() throws Exception {
        ResultActions results = postPropertyCreationForm();
        results.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl(samplePropertyUpdateUrl));
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
    public void propertyControllerRendersPropertyUpdateView() throws Exception {
        ResultActions results = performGetRequest(samplePropertyUpdateUrl);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTY_UPDATE_VIEW_NAME));
    }

    @Test
    public void propertyControllerGetsPropertyUsingTheSpecifiedHashCodeDuringPropertyUpdateViewAccess() throws Exception {
        performGetRequest(samplePropertyUpdateUrl);
        verify(userServiceMock).getPropertyForSaleByHashCode(userMock, propertyMock.hashCode());
    }

    @Test
    public void propertyControllerAssemblesTheViewModelFromThePropertyDuringPropertyUpdateViewAccess() throws Exception {
        when(userServiceMock.getPropertyForSaleByHashCode(userMock, propertyMock.hashCode())).thenReturn(propertyMock);
        performGetRequest(samplePropertyUpdateUrl);
        verify(propertyViewModelAssemblerMock).assemble(eq(propertyMock), eq(Optional.of(userMock)));
    }

    @Test
    public void propertyControllerReturnsHttpStatusNotFoundOnInvalidHashCodeDuringPropertyUpdateViewAccess() throws Exception {
        doThrow(new PropertyNotFoundException()).when(userServiceMock).getPropertyForSaleByHashCode(userMock, propertyMock.hashCode());
        ResultActions results = performGetRequest(samplePropertyUpdateUrl);
        results.andExpect(status().isNotFound());
    }

    @Test
    public void propertyControllerRendersPropertyUpdateConfirmationViewUponSuccessfulPropertyUpdate() throws Exception {
        ResultActions results = postPropertyUpdateForm();

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.PROPERTY_UPDATE_CONFIRMATION_VIEW_NAME));
    }

    @Test
    public void propertyControllerUpdatesThePropertyDuringPropertyUpdate() throws Exception {
        when(userServiceMock.getPropertyForSaleByHashCode(userMock, propertyMock.hashCode())).thenReturn(propertyMock);
        postPropertyUpdateForm();
        verify(propertyServiceMock).updateProperty(eq(propertyMock), any(PropertyDetails.class), any(BigDecimal.class));
    }

    @Test
    public void propertyControllerRendersAlertMessageOnPropertyServiceExceptionDuringPropertyUpdate() throws Exception {
        doThrow(new PropertyServiceException()).when(propertyServiceMock).updateProperty(anyObject(), anyObject(), anyObject());

        ResultActions results = postPropertyUpdateForm();

        results.andExpect(view().name(PropertyController.PROPERTY_UPDATE_VIEW_NAME));
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
        MockHttpServletRequestBuilder getRequest = buildDefaultGetRequest(PropertyController.PROPERTY_SEARCH_URL);
        getRequest.param(PROPERTY_SORT_COLUMN_PARAMETER_NAME, SAMPLE_PROPERTY_SORT_COLUMN.name());
        getRequest.param(PROPERTY_SORT_ORDER_PARAMETER_NAME, SAMPLE_SORT_ORDER.name());

        mockMvc.perform(getRequest);

        verify(propertyServiceMock).getPropertiesForSale(SAMPLE_PROPERTY_SORT_COLUMN, SAMPLE_SORT_ORDER);
    }

    @Test
    public void propertyControllerAssemblesTheViewModelFromThePropertiesDuringPropertySearchRequest() throws Exception {
        when(propertyServiceMock.getPropertiesForSale(any(PropertySortColumn.class), any(SortOrder.class))).thenReturn(SAMPLE_PROPERTY_LIST);
        performGetRequest(PropertyController.PROPERTY_SEARCH_URL);
        verify(propertyListViewModelAssemblerMock).assembleFromPropertyCollection(SAMPLE_PROPERTY_LIST);
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
        verify(propertyViewModelAssemblerMock).assemble(eq(propertyMock), eq(Optional.ofNullable(userMock)));
    }

    @Test
    public void propertyControllerReturnsHttpStatusNotFoundOnInvalidHashCodeDuringPropertyViewAccess() throws Exception {
        doThrow(new PropertyNotFoundException()).when(propertyServiceMock).getPropertyByHashCode(propertyMock.hashCode());
        ResultActions results = performPropertyGetRequest();
        results.andExpect(status().isNotFound());
    }

    @Test
    public void propertyControllerIncrementsThePropertyViewCountDuringPropertyViewAccess() throws Exception {
        when(propertyServiceMock.getPropertyByHashCode(propertyMock.hashCode())).thenReturn(propertyMock);
        performPropertyGetRequest();
        verify(propertyServiceMock).incrementPropertyViewCount(propertyMock);
    }

    @Test
    public void propertyControllerRendersMostPopularPropertiesView() throws Exception {
        MockHttpServletRequestBuilder getRequest = buildDefaultGetRequest(PropertyController.MOST_POPULAR_PROPERTIES_VIEW_URL)
                .param(PROPERTY_TYPE_REQUEST_PARAMETER_NAME, SAMPLE_PROPERTY_TYPE.name());

        ResultActions results = mockMvc.perform(getRequest);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.MOST_POPULAR_PROPERTIES_VIEW_NAME));
    }

    @Test
    public void propertyControllerRetrievesMostPopularPropertyListDuringMostPopularPropertiesViewAccess() throws Exception {
        MockHttpServletRequestBuilder getRequest = buildDefaultGetRequest(PropertyController.MOST_POPULAR_PROPERTIES_VIEW_URL)
                .param(PROPERTY_TYPE_REQUEST_PARAMETER_NAME, SAMPLE_PROPERTY_TYPE.name());
        mockMvc.perform(getRequest);
        verify(propertyServiceMock).getMostPopularProperties(eq(SAMPLE_PROPERTY_TYPE), anyInt());
    }

    @Test
    public void propertyControllerAssemblesTheViewModelFromThePropertyListDuringMostPopularPropertiesViewAccess() throws Exception {
        when(propertyServiceMock.getMostPopularProperties(eq(SAMPLE_PROPERTY_TYPE), anyInt())).thenReturn(SAMPLE_PROPERTY_LIST);
        MockHttpServletRequestBuilder getRequest = buildDefaultGetRequest(PropertyController.MOST_POPULAR_PROPERTIES_VIEW_URL)
                .param(PROPERTY_TYPE_REQUEST_PARAMETER_NAME, SAMPLE_PROPERTY_TYPE.name());

        mockMvc.perform(getRequest);

        verify(propertyListViewModelAssemblerMock).assembleFromPropertyCollection(SAMPLE_PROPERTY_LIST);
    }

    @Test
    public void propertyControllerRendersFavoritePropertiesView() throws Exception {
        ResultActions results = performGetRequest(PropertyController.FAVORITE_PROPERTIES_VIEW_URL);

        results.andExpect(status().isOk());
        results.andExpect(view().name(PropertyController.FAVORITE_PROPERTIES_VIEW_NAME));
    }

    @Test
    public void propertyControllerRetrievesFavoritePropertyListDuringFavoritePropertiesViewAccess() throws Exception {
        performGetRequest(PropertyController.FAVORITE_PROPERTIES_VIEW_URL);
        verify(userServiceMock).getFavoritePropertiesForSale(userMock);
    }

    @Test
    public void propertyControllerAssemblesTheViewModelFromTheFavoritePropertyListDuringPropertiesViewAccess() throws Exception {
        when(userMock.getFavoriteProperties()).thenReturn(SAMPLE_PROPERTY_SET);
        performGetRequest(PropertyController.FAVORITE_PROPERTIES_VIEW_URL);
        verify(propertyListViewModelAssemblerMock).assembleFromPropertyCollection(SAMPLE_PROPERTY_SET);
    }

    @Test
    public void propertyControllerGetsPropertyFromPropertyServiceUsingTheSpecifiedHashCodeDuringPropertyFavoriting() throws Exception {
        performPropertyFavoritingRequest();
        verify(propertyServiceMock).getPropertyByHashCode(propertyMock.hashCode());
    }

    @Test
    public void propertyControllerAddsTheSpecifiedPropertyToFavoritePropertiesOfUserDuringPropertyFavoriting() throws Exception {
        when(propertyServiceMock.getPropertyByHashCode(propertyMock.hashCode())).thenReturn(propertyMock);
        performPropertyFavoritingRequest();
        verify(userServiceMock).addFavoritePropertyToUser(userMock, propertyMock);
    }

    @Test
    public void propertyControllerReturnsOkHttpStatusOnSuccessfulPropertyFavoriting() throws Exception {
        ResultActions results = performPropertyFavoritingRequest();
        results.andExpect(status().isOk());
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

    private ResultActions postPropertyUpdateForm() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(samplePropertyUpdateUrl);
        postRequest.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest.session(mockHttpSession);

        return mockMvc.perform(postRequest);
    }

    private ResultActions performPropertyFavoritingRequest() throws Exception {
        MockHttpServletRequestBuilder postRequest = post(PropertyController.PROPERTY_FAVORITING_BASE_URL + propertyMock.hashCode());
        postRequest.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        postRequest.session(mockHttpSession);

        return mockMvc.perform(postRequest);
    }

    private ResultActions performPropertyGetRequest() throws Exception {
        return performGetRequest(PropertyController.PROPERTY_VIEW_BASE_URL + propertyMock.hashCode());
    }
}