package ca.ulaval.glo4003.housematch.spring.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.housematch.services.UserMailModificationException;
import ca.ulaval.glo4003.housematch.services.UserService;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.LoginFormViewModel;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.ProfileModificationFormViewModel;

@Controller
public class ProfileModificationController extends MvcController {

    @Autowired
    private UserService userService;

    protected ProfileModificationController() {
        // Required for Mockito
    }

    public ProfileModificationController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = PROFILE_URL, method = RequestMethod.GET)
    public final ModelAndView displayProfile(ModelMap modelMap, HttpSession session) {
        return new ModelAndView(PROFILE_VIEW_NAME);
    }

    @RequestMapping(value = MODIFY_USER_URL, method = RequestMethod.GET)
    public final ModelAndView modifyUserProfile(Model model, HttpSession session) {
        return new ModelAndView(MODIFY_USER_VIEW_NAME, MODIFY_USER_FORM_VIEWMODEL_NAME,
                new ProfileModificationFormViewModel());
    }

    @RequestMapping(value = MODIFY_USER_URL, method = RequestMethod.POST)
    public final ModelAndView modifyUserProfile(ProfileModificationFormViewModel modificationForm, ModelMap modelMap,
            HttpSession session, RedirectAttributes redirectAttributes)
                    throws UserNotFoundException, UserMailModificationException {
        User user = getUserFromHttpSession(session);
        userService.updateUserCoordinate(user.getUsername(), modificationForm.getAddress(),
                modificationForm.getPostalCode(), modificationForm.getCity(), modificationForm.getCountry(),
                modificationForm.getEmail());
        return new ModelAndView(new RedirectView(MODIFIED_USER_SAVED_URL));
    }

    @RequestMapping(value = MODIFIED_USER_SAVED_URL, method = RequestMethod.GET)
    public final ModelAndView displayProfileSaveView(HttpSession session) {
        return new ModelAndView(MODIFIED_USER_SAVED_VIEW_NAME);
    }

    @RequestMapping(value = EMAIL_MODIFICATION_URL, method = RequestMethod.GET)
    public final ModelAndView activate(@PathVariable Integer code, @PathVariable String user,
            RedirectAttributes redirectAttributes) {
        try {
            userService.completeEmailModification(code, user);
        } catch (UserNotFoundException e) {
            return showAlertMessage(LOGIN_VIEW_NAME, new LoginFormViewModel(), e.getMessage());
        }
        return new ModelAndView(MODIFIED_USER_SAVED_VIEW_NAME);

    }
}
