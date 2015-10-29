package ca.ulaval.glo4003.housematch.persistence.property;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

@XmlRootElement(name = "property")
public class XmlProperty {
    public PropertyType propertyType;
    public Address address;
    public BigDecimal sellingPrice;
    public PropertyDetails propertyDetails;
    public Date date;
}
