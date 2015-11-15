package ca.ulaval.glo4003.housematch.persistence.property;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.persistence.zoneddatetime.XmlZonedDateTimeAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@XmlRootElement(name = "property")
public class XmlProperty {
    public PropertyType propertyType;
    public Address address;
    public BigDecimal sellingPrice;
    public PropertyDetails propertyDetails;
    @XmlJavaTypeAdapter(XmlZonedDateTimeAdapter.class)
    public ZonedDateTime creationDate;
}
