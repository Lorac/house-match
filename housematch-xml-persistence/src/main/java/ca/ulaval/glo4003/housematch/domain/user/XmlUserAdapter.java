package ca.ulaval.glo4003.housematch.domain.user;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.jasypt.util.text.TextEncryptor;

public class XmlUserAdapter extends XmlAdapter<XmlUser, User> {

    private TextEncryptor textEncryptor;

    public XmlUserAdapter(final TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    @Override
    public User unmarshal(XmlUser xmlUser) throws Exception {
        User user = new User();
        user.username = xmlUser.username;
        user.password = textEncryptor.decrypt(xmlUser.password);
        user.email = xmlUser.email;
        user.role = xmlUser.role;
        user.activated = xmlUser.activated;
        user.address = xmlUser.address;
        user.postalCode = xmlUser.postalCode;
        user.city = xmlUser.city;
        user.country = xmlUser.country;
        return user;
    }

    @Override
    public XmlUser marshal(User user) throws Exception {
        XmlUser xmlUser = new XmlUser();
        xmlUser.username = user.username;
        xmlUser.password = textEncryptor.encrypt(user.password);
        xmlUser.email = user.email;
        xmlUser.role = user.role;
        xmlUser.activated = user.activated;
        xmlUser.address = user.address;
        xmlUser.postalCode = user.postalCode;
        xmlUser.city = user.city;
        xmlUser.country = user.country;
        return xmlUser;
    }

}
