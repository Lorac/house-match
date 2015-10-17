package ca.ulaval.glo4003.housematch.spring.web.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.services.property.PropertyService;
import ca.ulaval.glo4003.housematch.services.property.PropertyServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertyDetailsFormViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertySearchResultsViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.assemblers.PropertyViewModelAssembler;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyDetailsFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchResultsViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertyViewModel;

@Controller
public class PropertyController extends BaseController {

    public static final String PROPERTY_CREATION_URL = "/seller/sellProperty";
    static final String PROPERTY_CREATION_VIEW_NAME = "seller/propertyCreation";
    static final String PROPERTY_DETAILS_UPDATE_URL = "/seller/updatePropertyDetails/{propertyHashCode}";
    public static final String PROPERTY_DETAILS_UPDATE_BASE_URL = "/seller/updatePropertyDetails/";
    static final String PROPERTY_DETAILS_UPDATE_VIEW_NAME = "seller/propertyDetailsUpdate";
    static final String PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME = "seller/propertyDetailsUpdateConfirmation";
    public static final String PROPERTY_LIST_SELLER_URL = "/seller/propertyList";
    static final String PROPERTY_LIST_SELLER_VIEW_NAME = "seller/propertyList";
    public static final String PROPERTY_SEARCH_URL = "/buyer/searchProperties";
    public static final String PROPERTY_SEARCH_EXECUTE_URL = "/buyer/executePropertySearch";
    static final String PROPERTY_SEARCH_VIEW_NAME = "buyer/propertySearch";
    public static final String PROPERTY_VIEW_URL = "/buyer/propertyDetails/{propertyHashCode}";
    public static final String PROPERTY_VIEW_BASE_URL = "/buyer/propertyDetails/";
    static final String PROPERTY_VIEW_NAME = "buyer/propertyDetails";

    @Inject
    private PropertyService propertyService;
    @Inject
    private UserService userService;
    @Inject
    private PropertyViewModelAssembler propertyViewModelAssembler;
    @Inject
    private PropertyDetailsFormViewModelAssembler propertyDetailsFormViewModelAssembler;
    @Inject
    private PropertySearchResultsViewModelAssembler propertySearchResultsViewModelAssembler;

    protected PropertyController() {
        // Required for Mockito
    }

    public PropertyController(final PropertyService propertyService, final UserService userService,
            final PropertyViewModelAssembler propertyViewModelAssembler,
            final PropertyDetailsFormViewModelAssembler propertyDetailsFormViewModelAssembler,
            final PropertySearchResultsViewModelAssembler propertySearchResultsViewModelAssembler) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.propertyViewModelAssembler = propertyViewModelAssembler;
        this.propertyDetailsFormViewModelAssembler = propertyDetailsFormViewModelAssembler;
        this.propertySearchResultsViewModelAssembler = propertySearchResultsViewModelAssembler;
    }

    @RequestMapping(value = PROPERTY_CREATION_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyCreationPage(HttpSession httpSession) {
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
    public final ModelAndView displayPropertyDetailsUpdatePage(@PathVariable int propertyHashCode, ModelMap modelMap,
            HttpSession httpSession) {
        try {
            Property property = userService.getPropertyByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            modelMap.put(PropertyDetailsFormViewModel.NAME, propertyDetailsFormViewModelAssembler.assembleFromProperty(property));
            return new ModelAndView(PROPERTY_DETAILS_UPDATE_VIEW_NAME);
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = PROPERTY_DETAILS_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView updatePropertyDetails(@PathVariable int propertyHashCode, HttpSession httpSession,
            PropertyDetailsFormViewModel propertyDetailsForm) {
        try {
            Property property = userService.getPropertyByHashCode(getUserFromHttpSession(httpSession), propertyHashCode);
            propertyService.updatePropertyDetails(property, propertyDetailsForm.getDetails());
            return new ModelAndView(PROPERTY_DETAILS_UPDATE_CONFIRMATION_VIEW_NAME);
        } catch (PropertyNotFoundException | PropertyServiceException e) {
            return showAlertMessage(PROPERTY_DETAILS_UPDATE_VIEW_NAME, propertyDetailsForm, e.getMessage());
        }
    }

    @RequestMapping(value = PROPERTY_LIST_SELLER_URL, method = RequestMethod.GET)
    public final ModelAndView listSellerProperties() {
        return new ModelAndView(PROPERTY_LIST_SELLER_VIEW_NAME);
    }

    @RequestMapping(value = PROPERTY_SEARCH_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertySearchPage() {
        return new ModelAndView(PROPERTY_SEARCH_VIEW_NAME, PropertySearchFormViewModel.NAME, new PropertySearchFormViewModel());
    }

    @RequestMapping(value = PROPERTY_SEARCH_EXECUTE_URL, method = RequestMethod.GET)
    public final ModelAndView displaySearchResultPage(ModelMap modelMap, PropertySearchFormViewModel searchForm) {
        List<Property> properties = propertyService.getProperties();
        modelMap.put(PropertySearchFormViewModel.NAME, searchForm);
        modelMap.put(PropertySearchResultsViewModel.NAME, propertySearchResultsViewModelAssembler.assembleFromPropertyList(properties));
        return new ModelAndView(PROPERTY_SEARCH_VIEW_NAME, modelMap);
    }

    @RequestMapping(value = PROPERTY_VIEW_URL, method = RequestMethod.GET)
    public final ModelAndView displayPropertyPage(@PathVariable int propertyHashCode, ModelMap modelMap) {
        try {
            Property property = propertyService.getPropertyByHashCode(propertyHashCode);
            modelMap.put(PropertyViewModel.NAME, propertyViewModelAssembler.assembleFromProperty(property));
            return new ModelAndView(PROPERTY_VIEW_NAME);
        } catch (PropertyNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }
}
