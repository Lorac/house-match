package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;

import javax.validation.constraints.NotNull;

public class RegistrationFormViewModel extends ViewModel {

    public static final String NAME = "registrationForm";

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private UserRole role;

    public String getName() {
        return NAME;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
