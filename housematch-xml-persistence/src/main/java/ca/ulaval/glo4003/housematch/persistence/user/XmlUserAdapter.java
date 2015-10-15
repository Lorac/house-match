package ca.ulaval.glo4003.housematch.persistence.user;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.user.User;
import org.jasypt.util.text.TextEncryptor;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;

public class XmlUserAdapter extends XmlAdapter<XmlUser, User> {

    private PropertyRepository propertyRepository;
    private TextEncryptor textEncryptor;

    public XmlUserAdapter(final PropertyRepository propertyRepository, final TextEncryptor textEncryptor) {
        this.propertyRepository = propertyRepository;
        this.textEncryptor = textEncryptor;
    }

    @Override
    public User unmarshal(XmlUser xmlUser) throws Exception {
        User user = new User(xmlUser.username, xmlUser.email, textEncryptor.decrypt(xmlUser.password), xmlUser.role);
        user.setActivationCode(xmlUser.activationCode);
        user.setActivated(xmlUser.activated);
        user.setAddress(xmlUser.address);
        user.setProperties(dereferenceProperties(xmlUser));
        return user;
    }

    private List<Property> dereferenceProperties(XmlUser xmlUser) throws PropertyNotFoundException {
        List<Property> properties = new ArrayList<Property>();
        for (Integer propertyHashCode : xmlUser.propertyRef) {
            properties.add(propertyRepository.getByHashCode(propertyHashCode));
        }
        return properties;
    }

    @Override
    public XmlUser marshal(User user) throws Exception {
        XmlUser xmlUser = new XmlUser();
        xmlUser.username = user.getUsername();
        xmlUser.password = textEncryptor.encrypt(user.getPassword());
        xmlUser.email = user.getEmail();
        xmlUser.role = user.getRole();
        xmlUser.activationCode = user.getActivationCode();
        xmlUser.activated = user.isActivated();
        xmlUser.address = user.getAddress();

        for (Property property : user.getProperties()) {
            xmlUser.propertyRef.add(property.hashCode());
        }

        return xmlUser;
    }
}
