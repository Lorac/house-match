package ca.ulaval.glo4003.housematch.persistence.property;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlZonedDateTimeAdapter;

@XmlRootElement(name = "property")
public class XmlProperty {
    public PropertyType propertyType;
    public Address address;
    public BigDecimal sellingPrice;
    public List<BigDecimal> sellingPriceHistory = new ArrayList<>();
    public PropertyDetails propertyDetails;
    public Integer viewCount;
    @XmlJavaTypeAdapter(XmlZonedDateTimeAdapter.class)
    public ZonedDateTime creationDate;
    public PropertyStatus status;
    @XmlElementWrapper(name = "photos")
    @XmlElement(name = "propertyPhotoHashCode")
    public Set<Integer> photos = new HashSet<>();
}
