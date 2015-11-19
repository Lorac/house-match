package ca.ulaval.glo4003.housematch.web.viewmodels;

import javax.validation.constraints.NotNull;

import ca.ulaval.glo4003.housematch.domain.address.Address;

public final class ContactInformationFormViewModel extends ViewModel {

    public static final String NAME = "contactInformation";

    @NotNull
    private Address address;
    @NotNull
    private String email;

    @Override
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
