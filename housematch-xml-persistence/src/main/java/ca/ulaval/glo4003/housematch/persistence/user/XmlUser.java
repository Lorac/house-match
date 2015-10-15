package ca.ulaval.glo4003.housematch.persistence.user;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "user")
public class XmlUser {
    public String username;
    public String email;
    public String password;
    public UserRole role;
    public UUID activationCode;
    public boolean activated;
    public Address address;
    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "propertyRef")
    public List<Integer> propertyRef = new ArrayList<Integer>();
}
