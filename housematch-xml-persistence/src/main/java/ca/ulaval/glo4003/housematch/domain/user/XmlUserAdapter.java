package ca.ulaval.glo4003.housematch.domain.user;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ca.ulaval.glo4003.housematch.persistence.XmlTextEncryptor;

public class XmlUserAdapter extends XmlAdapter<XmlUser, User> {

    private XmlTextEncryptor xmlTextEncryptor;

    public XmlUserAdapter() {
        this(new XmlTextEncryptor());
    }

    public XmlUserAdapter(XmlTextEncryptor xmlTextEncryptor) {
        this.xmlTextEncryptor = xmlTextEncryptor;
    }

    @Override
    public User unmarshal(XmlUser xmlUser) throws Exception {
        User user = new User();
        user.username = xmlUser.username;
        user.password = xmlTextEncryptor.decrypt(xmlUser.password);
        user.email = xmlUser.email;
        user.role = xmlUser.role;
        user.activated = xmlUser.activated;
        return user;
    }

    @Override
    public XmlUser marshal(User user) throws Exception {
        XmlUser xmlUser = new XmlUser();
        xmlUser.username = user.username;
        xmlUser.password = xmlTextEncryptor.encrypt(user.password);
        xmlUser.email = user.email;
        xmlUser.role = user.role;
        xmlUser.activated = user.activated;
        return xmlUser;
    }

}
