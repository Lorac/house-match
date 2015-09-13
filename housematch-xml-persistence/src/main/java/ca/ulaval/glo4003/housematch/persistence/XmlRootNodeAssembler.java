package ca.ulaval.glo4003.housematch.persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.housematch.domain.user.User;

@XmlRootElement(name = "HouseMatch")
public class XmlRootNodeAssembler {

    private List<User> users;

    protected XmlRootNodeAssembler() {
        // Required for JAXB
    }

    @XmlElementWrapper(name = "Users")
    @XmlElement(name = "User")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
