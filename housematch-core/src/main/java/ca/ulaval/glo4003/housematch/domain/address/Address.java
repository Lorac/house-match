package ca.ulaval.glo4003.housematch.domain.address;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Address {

    Integer streetNumber;
    String streetName;
    String additionalAddressInfo;
    String town;
    String postCode;
    Region region;

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAdditionalAddressInfo() {
        return additionalAddressInfo;
    }

    public void setAdditionalAddressInfo(String additionalAddressInfo) {
        this.additionalAddressInfo = additionalAddressInfo;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(streetNumber).append(streetName).append(town).append(additionalAddressInfo)
                .append(region.ordinal()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Address address = (Address) obj;
        return new EqualsBuilder().append(streetNumber, address.streetNumber).append(streetName, address.streetName)
                .append(town, address.town).append(additionalAddressInfo, address.additionalAddressInfo).append(region, address.region)
                .isEquals();
    }
}
