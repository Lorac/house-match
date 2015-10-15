package ca.ulaval.glo4003.housematch.spring.web.security;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.spring.web.controllers.BaseController;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        if (response.isCommitted()) {
            return;
        }

        addUserToHttpSession(request.getSession(), authentication);

        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private void addUserToHttpSession(HttpSession httpSession, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        httpSession.setAttribute(BaseController.USER_ATTRIBUTE_NAME, user);
    }

    protected String determineTargetUrl(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (!user.isActivated()) {
            return BaseController.EMAIL_RECONFIRM_URL;
        }
        return determineTargetUrlFromAuthorities(authentication.getAuthorities());
    }

    private String determineTargetUrlFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        if (isAdmin(roles)) {
            return BaseController.ADMIN_HOME_URL;
        } else if (isBuyer(roles)) {
            return BaseController.BUYER_HOME_URL;
        } else if (isSeller(roles)) {
            return BaseController.SELLER_HOME_URL;
        } else {
            return BaseController.HOME_URL;
        }
    }

    private boolean isBuyer(List<String> roles) {
        return roles.contains("Buyer");
    }

    private boolean isAdmin(List<String> roles) {
        return roles.contains("Administrator");
    }

    private boolean isSeller(List<String> roles) {
        return roles.contains("Seller");
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

}
