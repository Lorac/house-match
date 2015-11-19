package ca.ulaval.glo4003.housematch.persistence.user;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserFactory;

public class XmlUserAdapter extends XmlAdapter<XmlUser, User> {

    private UserFactory userFactory;
    private PropertyRepository propertyRepository;

    public XmlUserAdapter(final UserFactory userFactory, final PropertyRepository propertyRepository) {
        this.userFactory = userFactory;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public User unmarshal(XmlUser xmlUser) throws Exception {
        User user = userFactory.createUser(xmlUser.username, xmlUser.email, xmlUser.passwordHash, xmlUser.role);
        user.setPasswordHash(xmlUser.passwordHash);
        user.setActivationCode(xmlUser.activationCode);
        user.setActivated(xmlUser.activated);
        user.setAddress(xmlUser.address);
        user.setStatus(xmlUser.status);
        user.setLastLoginDate(xmlUser.lastLoginDate);
        user.setPropertiesForSale(dereferenceProperties(xmlUser.propertiesForSale));
        user.setPurchasedProperties(dereferenceProperties(xmlUser.purchasedProperties));
        return user;
    }

    private List<Property> dereferenceProperties(List<Integer> propertyHashCodes) throws PropertyNotFoundException {
        List<Property> properties = new ArrayList<Property>();
        for (Integer propertyHashCode : propertyHashCodes) {
            properties.add(propertyRepository.getByHashCode(propertyHashCode));
        }
        return properties;
    }

    @Override
    public XmlUser marshal(User user) throws Exception {
        XmlUser xmlUser = new XmlUser();
        xmlUser.username = user.getUsername();
        xmlUser.passwordHash = user.getPasswordHash();
        xmlUser.email = user.getEmail();
        xmlUser.role = user.getRole();
        xmlUser.activationCode = user.getActivationCode();
        xmlUser.activated = user.isActivated();
        xmlUser.address = user.getAddress();
        xmlUser.status = user.getStatus();
        xmlUser.lastLoginDate = user.getLastLoginDate();
        xmlUser.propertiesForSale = referenceProperties(user.getPropertiesForSale());
        xmlUser.purchasedProperties = referenceProperties(user.getPurchasedProperties());
        return xmlUser;
    }

    private List<Integer> referenceProperties(List<Property> properties) {
        List<Integer> propertyHashCodes = new ArrayList<Integer>();
        for (Property property : properties) {
            propertyHashCodes.add(property.hashCode());
        }
        return propertyHashCodes;
    }
}
