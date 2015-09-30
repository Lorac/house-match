package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import javax.validation.constraints.NotNull;

public final class EmailReconfirmFormViewModel {

    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
