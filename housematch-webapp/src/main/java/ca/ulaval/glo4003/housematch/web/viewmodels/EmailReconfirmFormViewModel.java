package ca.ulaval.glo4003.housematch.web.viewmodels;

import javax.validation.constraints.NotNull;

public final class EmailReconfirmFormViewModel extends ViewModel {

    public static final String NAME = "emailReconfirmFormViewModel";

    @NotNull
    private String email;

    @Override
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
