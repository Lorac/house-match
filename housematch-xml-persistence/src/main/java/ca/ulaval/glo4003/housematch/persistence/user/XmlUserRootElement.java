package ca.ulaval.glo4003.housematch.persistence.user;

import ca.ulaval.glo4003.housematch.domain.user.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "housematch")
public class XmlUserRootElement {

    private List<User> users;

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    @XmlJavaTypeAdapter(XmlUserAdapter.class)
    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
