package ca.ulaval.glo4003.housematch.domain.streetaddress;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class StreetAddress {

    Integer addressNumber;
    String streetName;
    String additionalAddressInfo;
    String zipCode;
    State state;

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

    public String getAdditionalAddressInfo() {
        return additionalAddressInfo;
    }

    public void setAdditionalAddressInfo(String additionalAddressInfo) {
        this.additionalAddressInfo = additionalAddressInfo;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(addressNumber).append(streetName).append(additionalAddressInfo)
                .append(state.ordinal()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StreetAddress)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        StreetAddress streetAddress = (StreetAddress) obj;
        return new EqualsBuilder().append(addressNumber, streetAddress.addressNumber)
                .append(streetName, streetAddress.streetName)
                .append(additionalAddressInfo, streetAddress.additionalAddressInfo)
                .append(state, streetAddress.state).isEquals();
    }
}
