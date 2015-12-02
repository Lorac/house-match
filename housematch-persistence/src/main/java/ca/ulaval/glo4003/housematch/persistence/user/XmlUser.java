package ca.ulaval.glo4003.housematch.persistence.user;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;
import ca.ulaval.glo4003.housematch.domain.user.UserStatus;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlZonedDateTimeAdapter;

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
    public Set<Integer> propertiesForSale = new HashSet<>();
    @XmlElementWrapper(name = "purchasedProperties")
    @XmlElement(name = "propertyHashCode")
    public Set<Integer> purchasedProperties = new HashSet<>();
    @XmlElementWrapper(name = "favoriteProperties")
    @XmlElement(name = "propertyHashCode")
    public Set<Integer> favoriteProperties = new HashSet<>();
    public Queue<Notification> notificationsQueue = new ConcurrentLinkedQueue<>();
}
