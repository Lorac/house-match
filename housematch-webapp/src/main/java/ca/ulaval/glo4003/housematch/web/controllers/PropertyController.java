package ca.ulaval.glo4003.housematch.web.controllers;

import ca.ulaval.glo4003.housematch.domain.SortOrder;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertySortColumn;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.web.assemblers.PropertyDetailsFormViewModelAssembler;
import ca.ulaval.glo4003.housematch.web.assemblers.PropertyListViewModelAssembler;
import ca.ulaval.glo4003.housematch.web.assemblers.PropertyViewModelAssembler;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyDetailsFormViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyListViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PropertyController extends BaseController {

    public static final String PROPERTY_CREATION_URL = "/seller/sellProperty";
    public static final String PROPERTY_DETAILS_UPDATE_URL = "/seller/updatePropertyDetails/{propertyHashCode}";
    public static final String PROPERTY_DETAILS_UPDATE_BASE_URL = "/seller/updatePropertyDetails/";
    public static final String PROPERTIES_FOR_SALE_LIST_URL = "/seller/propertyList";
    public static final String PROPERTY_SEARCH_URL = "/buyer/searchProperties";
    public static final String PROPERTY_VIEW_URL = "/buyer/viewProperty/{propertyHashCode}";
    public static final String PROPERTY_VIEW_BASE_URL = "/buyer/viewProperty/";
    public static final String MOST_POPULAR_PROPERTIES_VIEW_URL = "/mostPopularProperties";
    public static final String FAVORITE_PROPERTIES_VIEW_URL = "buyer/favoriteProperties";
    public static final String FAVORITE_PROPERTIES_VIEW_NAME = "buyer/favoriteProperties";
    public static final String ADD_PROPERTY_TO_FAVORITE_VIEW_URL = "buyer/addPropertyToFavorite/{propertyHashCode}";
    static final String PROPERTY_CREATION_VIEW_NAME = "seller/propertyCreation";
    static final String PROPERTY_DETAILS_UPDATE_VIEW_NAME = "seller/propertyDetailsUpdate";
    static final String PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME = "seller/propertyDetailsUpdateConfirmation";
    static final String PROPERTIES_FOR_SALE_LIST_VIEW_NAME = "seller/propertyList";
    static final String PROPERTY_SEARCH_VIEW_NAME = "buyer/propertySearch";
    static final String PROPERTY_VIEW_NAME = "buyer/propertyDetails";
    static final String MOST_POPULAR_PROPERTIES_VIEW_NAME = "home/mostPopularProperties";
    private static final Integer MOST_POPULAR_PROPERTIES_DISPLAY_LIMIT = 10;


    @Inject
    private PropertyService propertyService;
    @Inject
    private UserService userService;
    @Inject
    private PropertyViewModelAssembler propertyViewModelAssembler;
    @Inject
    private PropertyDetailsFormViewModelAssembler propertyDetailsFormViewModelAssembler;
    @Inject
    private PropertyListViewModelAssembler propertyListViewModelAssembler;

    protected PropertyController() {
        // Required for Mockito
    }

    public PropertyController(final PropertyService propertyService, final UserService userService,
            final PropertyViewModelAssembler propertyViewModelAssembler,
            final PropertyDetailsFormViewModelAssembler propertyDetailsFormViewModelAssembler,
            final PropertyListViewModelAssembler propertyListViewModelAssembler) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.propertyViewModelAssembler = propertyViewModelAssembler;
        this.propertyDetailsFormViewModelAssembler = propertyDetailsFormViewModelAssembler;
        this.propertyListViewModelAssembler = propertyListViewModelAssembler;
    }

    @RequestMapping(value = PROPERTY_CREATION_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyCreationView(HttpSession httpSession) {
        return new ModelAndView(PROPERTY_CREATION_VIEW_NAME, PropertyViewModel.NAME, new PropertyViewModel());
    }

    @RequestMapping(value = PROPERTY_CREATION_URL, method = RequestMethod.POST)
    public final ModelAndView createProperty(PropertyViewModel propertyViewModel, HttpSession httpSession) {
        try {
            Property property = propertyService.createProperty(propertyViewModel.getPropertyType(), propertyViewModel.getAddress(),
                    propertyViewModel.getSellingPrice(), getUserFromHttpSession(httpSession));
            return new ModelAndView(new RedirectView(PROPERTY_DETAILS_UPDATE_BASE_URL + property.hashCode()));
        } catch (PropertyServiceException e) {
            return showAlertMessage(PROPERTY_CREATION_VIEW_NAME, propertyViewModel, e.getMessage());
        }
    }

    @RequestMapping(value = PROPERTY_DETAILS_UPDATE_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyDetailsUpdateView(@PathVariable int propertyHashCode, ModelMap modelMap,
            HttpSession httpSession) {
        try {
            Property property = userService.getPropertyForSaleByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            modelMap.put(PropertyDetailsFormViewModel.NAME, propertyDetailsFormViewModelAssembler.assembleFromProperty(property));
            return new ModelAndView(PROPERTY_DETAILS_UPDATE_VIEW_NAME);
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = PROPERTY_DETAILS_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView updatePropertyDetails(@PathVariable int propertyHashCode, HttpSession httpSession,
            PropertyDetailsFormViewModel propertyDetailsFormViewModel) {
        try {
            Property property = userService.getPropertyForSaleByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            propertyService.updatePropertyDetails(property, propertyDetailsFormViewModel.getDetails());
            return new ModelAndView(PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME);
        } catch (PropertyNotFoundException | PropertyServiceException e) {
            return showAlertMessage(PROPERTY_DETAILS_UPDATE_VIEW_NAME, propertyDetailsFormViewModel, e.getMessage());
        }
    }

    @RequestMapping(value = ADD_PROPERTY_TO_FAVORITE_VIEW_URL, method = RequestMethod.POST)
    public final ModelAndView addPropertyToFavorite(@PathVariable int propertyHashCode, HttpSession httpSession) {
        try {
            Property property = propertyService.getPropertyByHashCode(propertyHashCode);
            User user = getUserFromHttpSession(httpSession);
            userService.addFavoritePropertyToUser(user, property);
            return new ModelAndView(PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME);
        } catch (PropertyNotFoundException e) {
            return showAlertMessage(PROPERTY_DETAILS_UPDATE_VIEW_NAME, new PropertyDetailsFormViewModel(), e.getMessage());
        }
    }

    @RequestMapping(value = PROPERTIES_FOR_SALE_LIST_URL, method = RequestMethod.GET)
    public final ModelAndView listPropertiesForSale() {
        return new ModelAndView(PROPERTIES_FOR_SALE_LIST_VIEW_NAME);
    }

    @RequestMapping(value = PROPERTY_SEARCH_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertySearchView(ModelMap modelMap,
            @RequestParam(value = "sortColumn", defaultValue = "NONE") PropertySortColumn sortColumn,
            @RequestParam(value = "sortOrder", defaultValue = "NONE") SortOrder sortOrder) {
        List<Property> properties = propertyService.getProperties(sortColumn, sortOrder);
        modelMap.put(PropertyListViewModel.NAME, propertyListViewModelAssembler.assembleFromPropertyList(properties));
        return new ModelAndView(PROPERTY_SEARCH_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = PROPERTY_VIEW_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyView(@PathVariable int propertyHashCode) {
        try {
            Property property = propertyService.getPropertyByHashCode(propertyHashCode);
            propertyService.incrementPropertyViewCount(property);
            return new ModelAndView(PROPERTY_VIEW_NAME, PropertyViewModel.NAME, propertyViewModelAssembler.assembleFromProperty(property));
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = MOST_POPULAR_PROPERTIES_VIEW_URL, method = RequestMethod.GET)
    public final ModelAndView displayMostPopularProperties(@RequestParam("propertyType") PropertyType propertyType) {
        List<Property> properties = propertyService.getMostPopularProperties(propertyType, MOST_POPULAR_PROPERTIES_DISPLAY_LIMIT);
        PropertyListViewModel viewModel = propertyListViewModelAssembler.assembleFromPropertyList(properties);
        return new ModelAndView(MOST_POPULAR_PROPERTIES_VIEW_NAME, PropertyListViewModel.NAME, viewModel);
    }

    @RequestMapping(value = FAVORITE_PROPERTIES_VIEW_URL)
    public final ModelAndView displayFavoriteProperties(HttpSession httpSession) {
        User user = getUserFromHttpSession(httpSession);
        List<Property> favoriteProperties = user.getFavoriteProperties();
        PropertyListViewModel viewModel = propertyListViewModelAssembler.assembleFromPropertyList(favoriteProperties);
        return new ModelAndView(FAVORITE_PROPERTIES_VIEW_NAME, PropertyListViewModel.NAME, viewModel);
    }
}
