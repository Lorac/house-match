package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import javax.validation.constraints.NotNull;

public final class EmailReconfirmFormViewModel extends ViewModel {

    public static final String NAME = "emailReconfirmForm";

    @NotNull
    private String email;

    public String getName() {
        return NAME;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
