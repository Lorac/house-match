package ca.ulaval.glo4003.housematch.persistence.marshalling;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.XmlUserAdapter;

@XmlRootElement(name = "HouseMatch")
public class XmlRootElementNode {

    private List<User> users;

    protected XmlRootElementNode() {
        // Required for JAXB
    }

    @XmlElementWrapper(name = "Users")
    @XmlElement(name = "User")
    @XmlJavaTypeAdapter(XmlUserAdapter.class)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
