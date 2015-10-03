package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.UserProfileFormViewModel;

@Controller
public class UserProfileController extends MvcController {

    @Autowired
    private UserService userService;

    protected UserProfileController() {
        // Required for Mockito
    }

    public UserProfileController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = PROFILE_URL, method = RequestMethod.GET)
    public final ModelAndView displayProfile(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute(USER_ATTRIBUTE_NAME);
        modelMap.addAttribute(PROFILE_ADDRESS_ATTRIBUTE_NAME, user.getAddress());
        modelMap.addAttribute(PROFILE_POSTAL_CODE_ATTRIBUTE_NAME, user.getPostalCode());
        modelMap.addAttribute(PROFILE_CITY_ATTRIBUTE_NAME, user.getCity());
        modelMap.addAttribute(PROFILE_COUNTRY_ATTRIBUTE_NAME, user.getCountry());
        modelMap.addAttribute(PROFILE_EMAIL_ATTRIBUTE_NAME, user.getEmail());
        return new ModelAndView(PROFILE_VIEW_NAME, PROFILE_FORM_VIEWMODEL_NAME, new UserProfileFormViewModel());
    }

    @RequestMapping(value = PROFILE_URL, method = RequestMethod.POST)
    public final ModelAndView userProfile(UserProfileFormViewModel profileForm, ModelMap modelMap, HttpSession session,
            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute(USER_ATTRIBUTE_NAME);
        userService.updateUserCoordinate(user, profileForm.getAddress(), profileForm.getPostalCode(),
                profileForm.getCity(), profileForm.getCountry());
        // TODO - Update user email and revalidate if the email changed.
        return new ModelAndView(new RedirectView(PROFILE_SAVED_URL));
    }

    @RequestMapping(value = PROFILE_SAVED_URL, method = RequestMethod.GET)
    public final ModelAndView displayProfileSaveView(HttpSession session) {
        return new ModelAndView(PROFILE_SAVED_VIEW_NAME);
    }
}
