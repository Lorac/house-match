package ca.ulaval.glo4003.housematch.domain.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "User")
public class XmlUser {
    String username;
    String email;
    String password;
    UserRole role;
    boolean activated;
}
