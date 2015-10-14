package ca.ulaval.glo4003.housematch.persistence.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;

@XmlRootElement(name = "user")
public class XmlUser {
    public String username;
    public String email;
    public String password;
    public UserRole role;
    public UUID activationCode;
    public boolean activated;

    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "propertyRefs")
    public List<Integer> propertyRefs = new ArrayList<Integer>();
}
