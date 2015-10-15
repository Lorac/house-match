package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import ca.ulaval.glo4003.housematch.domain.address.Address;

import javax.validation.constraints.NotNull;

public final class ContactInformationFormViewModel extends ViewModel {

    public static final String NAME = "contactInformationForm";

    @NotNull
    private Address address;
    @NotNull
    private String email;

    public String getName() {
        return NAME;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}