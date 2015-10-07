package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.services.user.UserActivationServiceException;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.spring.web.security.AuthorizationValidator;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.UserProfileFormViewModel;
import ca.ulaval.glo4003.housematch.validators.address.AddressValidationException;

@Controller
public class UserProfileController extends MvcController {

    @Autowired
    private UserService userService;

    protected UserProfileController() {
        // Required for Mockito
    }

    public UserProfileController(final AuthorizationValidator authorizationValidator, final UserService userService) {
        this.authorizationValidator = authorizationValidator;
        this.userService = userService;
    }

    @RequestMapping(value = CONTACT_INFO_UPDATE_URL, method = RequestMethod.GET)
    public final ModelAndView modifyUserProfile(HttpSession httpSession) throws AuthenticationException {
        authorizationValidator.validateResourceAccess(CONTACT_INFO_UPDATE_VIEW_NAME, httpSession, USER_ATTRIBUTE_NAME);
        return new ModelAndView(CONTACT_INFO_UPDATE_VIEW_NAME, UserProfileFormViewModel.VIEWMODEL_NAME,
                new UserProfileFormViewModel());
    }

    @RequestMapping(value = CONTACT_INFO_UPDATE_URL, method = RequestMethod.POST)
    public final ModelAndView modifyUserProfile(UserProfileFormViewModel userProfileForm, HttpSession httpSession)
            throws UserNotFoundException, UserActivationServiceException, AuthenticationException {
        authorizationValidator.validateResourceAccess(CONTACT_INFO_UPDATE_VIEW_NAME, httpSession, USER_ATTRIBUTE_NAME);
        try {
            userService.updateUserCoordinate(getUserFromHttpSession(httpSession), userProfileForm.getAddress(),
                    userProfileForm.getEmail());
            return new ModelAndView(CONTACT_INFO_UPDATE_CONFIRMATION_VIEW_NAME);
        } catch (AddressValidationException e) {
            return showAlertMessage(CONTACT_INFO_UPDATE_VIEW_NAME, userProfileForm, e.getMessage());
        }

    }

}
