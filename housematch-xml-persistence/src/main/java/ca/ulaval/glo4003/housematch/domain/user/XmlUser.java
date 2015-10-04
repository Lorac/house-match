package ca.ulaval.glo4003.housematch.domain.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "User")
public class XmlUser {
    public String username;
    public String email;
    public String password;
    public UserRole role;
    public boolean activated;
    public String address;
    public String postalCode;
    public String city;
    public String country;
}
