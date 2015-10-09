package ca.ulaval.glo4003.housematch.domain.address;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Address {

    Integer addressNumber;
    String streetName;
    String additionalAddressInfo;
    String city;
    String postCode;
    Region region;

    
    public String getCity(){
    	return this.city;
    }
    
    public void setCity(String city) {
    	this.city = city;
    }
    
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
        return new HashCodeBuilder().append(addressNumber).append(streetName).append(additionalAddressInfo)
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
        return new EqualsBuilder().append(addressNumber, address.addressNumber)
                .append(streetName, address.streetName)
                .append(additionalAddressInfo, address.additionalAddressInfo)
                .append(region, address.region).isEquals();
    }
}
