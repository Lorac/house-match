package ca.ulaval.glo4003.housematch.persistence.user;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.XmlUserAdapter;

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
