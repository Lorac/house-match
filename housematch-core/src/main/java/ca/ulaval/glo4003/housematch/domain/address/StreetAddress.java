package ca.ulaval.glo4003.housematch.domain.address;

public class StreetAddress {

    Integer addressNumber;
    String streetName;
    String additionalAddressFields;
    String zipCode;
    State state;
    Country country;

    public Integer getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(Integer addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAdditionalAddressFields() {
        return additionalAddressFields;
    }

    public void setAdditionalAddressFields(String additionalAddressFields) {
        this.additionalAddressFields = additionalAddressFields;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
