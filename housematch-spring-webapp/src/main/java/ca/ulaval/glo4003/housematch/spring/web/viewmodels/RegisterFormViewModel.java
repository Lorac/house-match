package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import java.util.ArrayList;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;

public class RegisterFormViewModel {

    private String username;
    private String password;
    private String email;
    private UserRole role;
    private ArrayList<UserRole> registerableRoles;

    public RegisterFormViewModel() {

    }

    public RegisterFormViewModel(final ArrayList<UserRole> registerableRoles) {
        this.registerableRoles = registerableRoles;
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

    public ArrayList<UserRole> getRegisterableRoles() {
        return registerableRoles;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
