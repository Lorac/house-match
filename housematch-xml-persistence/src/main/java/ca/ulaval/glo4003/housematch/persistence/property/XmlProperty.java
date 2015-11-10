package ca.ulaval.glo4003.housematch.persistence.property;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "property")
public class XmlProperty {
    public PropertyType propertyType;
    public Address address;
    public BigDecimal sellingPrice;
    public PropertyDetails propertyDetails;
    public String date;
}
