package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import javax.validation.constraints.NotNull;

public final class LoginFormViewModel extends ViewModel {

    public static final String NAME = "loginForm";

    @NotNull
    private String username;
    @NotNull
    private String password;

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
}
