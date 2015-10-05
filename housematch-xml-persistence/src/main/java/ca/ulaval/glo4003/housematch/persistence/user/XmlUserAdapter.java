package ca.ulaval.glo4003.housematch.persistence.user;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.jasypt.util.text.TextEncryptor;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.user.User;

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
        user.setPropertyListings(dereferencePropertyListings(xmlUser));
        return user;
    }

    private List<Property> dereferencePropertyListings(XmlUser xmlUser) throws PropertyNotFoundException {
        List<Property> propertyListings = new ArrayList<Property>();
        for (Integer propertyHashCode : xmlUser.propertyListingsRef) {
            propertyListings.add(propertyRepository.getByHashCode(propertyHashCode));
        }
        return propertyListings;
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

        for (Property property : user.getPropertyListings()) {
            xmlUser.propertyListingsRef.add(property.hashCode());
        }

        return xmlUser;
    }
}
