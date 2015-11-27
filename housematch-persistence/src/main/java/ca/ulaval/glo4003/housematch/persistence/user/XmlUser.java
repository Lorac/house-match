package ca.ulaval.glo4003.housematch.persistence.user;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlZonedDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "user")
public class XmlUser {
    public String username;
    public String email;
    public String passwordHash;
    public UserRole role;
    public UserStatus status;
    public UUID activationCode;
    public boolean activated;
    public Address address;
    @XmlJavaTypeAdapter(XmlZonedDateTimeAdapter.class)
    public ZonedDateTime lastLoginDate;
    @XmlElementWrapper(name = "propertiesForSale")
    @XmlElement(name = "propertyHashCode")
    public List<Integer> propertiesForSale = new ArrayList<>();
    @XmlElementWrapper(name = "purchasedProperties")
    @XmlElement(name = "propertyHashCode")
    public List<Integer> purchasedProperties = new ArrayList<>();
    @XmlElementWrapper(name = "favoriteProperties")
    @XmlElement(name = "propertyHashCode")
    public List<Integer> favoriteProperties = new ArrayList<>();
}
