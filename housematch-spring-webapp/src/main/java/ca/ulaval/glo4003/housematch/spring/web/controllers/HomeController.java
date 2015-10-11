package ca.ulaval.glo4003.housematch.spring.web.controllers;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends MvcController {

    protected HomeController() {
        // Required for Mockito
    }

    @RequestMapping(value = HOME_URL, method = RequestMethod.GET)
    public final ModelAndView displayHomeView(HttpSession httpSession) throws AuthenticationException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> grantedAuthorities = authentication.getAuthorities().stream().collect(Collectors.toList());

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            String authority = grantedAuthority.getAuthority();
            if (authority.equals(UserRole.ADMINISTRATOR.getDisplayName())) {
                displayAdminHomeView();
            } else if (authority.equals(UserRole.SELLER.getDisplayName())) {
                displaySellerHomeView();
            } else if (authority.equals(UserRole.BUYER.getDisplayName())) {
                displayBuyerHomeView();
            }
        }
        return new ModelAndView(HOME_VIEW_NAME);
    }

    private ModelAndView displayAdminHomeView() throws AuthenticationException {
        return new ModelAndView(ADMIN_HOME_VIEW_NAME);
    }

    private ModelAndView displayBuyerHomeView() throws AuthenticationException {
        return new ModelAndView(BUYER_HOME_VIEW_NAME);
    }

    private ModelAndView displaySellerHomeView() throws AuthenticationException {
        return new ModelAndView(SELLER_HOME_VIEW_NAME);
    }
}
