package ca.ulaval.glo4003.housematch.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "users")
public class XmlUserRootElement {

    private List<User> users = new ArrayList<User>();

    @XmlElement(name = "user")
    @XmlJavaTypeAdapter(XmlUserAdapter.class)
    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
