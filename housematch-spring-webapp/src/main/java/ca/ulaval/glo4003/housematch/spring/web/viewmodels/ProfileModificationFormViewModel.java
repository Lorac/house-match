package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import javax.validation.constraints.NotNull;

public final class ProfileModificationFormViewModel {

    @NotNull
    private String address;
    @NotNull
    private String postCode;
    @NotNull
    private String city;
    @NotNull
    private String region;
    @NotNull
    private String country;
    @NotNull
    private String email;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
