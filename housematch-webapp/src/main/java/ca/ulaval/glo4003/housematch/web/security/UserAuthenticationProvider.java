package ca.ulaval.glo4003.housematch.web.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.services.user.UserService;
import ca.ulaval.glo4003.housematch.services.user.UserServiceException;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private UserService userService;

    public UserAuthenticationProvider(final UserService userService) {
        this.userService = userService;
    }

    protected UserAuthenticationProvider() {
        // Required for bean instanciation
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();

        try {
            User user = userService.getUserByLoginCredentials(name, password);
            return createAuthenticationToken(user, password);
        } catch (UserServiceException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    private Authentication createAuthenticationToken(User user, String password) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().getDisplayName()));
        return new UsernamePasswordAuthenticationToken(user, password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
