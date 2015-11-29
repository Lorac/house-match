package ca.ulaval.glo4003.housematch.persistence.user;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        user.setFavoriteProperties(dereferenceProperties(xmlUser.favoriteProperties));
        return user;
    }

    private Set<Property> dereferenceProperties(Set<Integer> propertyHashCodes) throws PropertyNotFoundException {
        Set<Property> properties = new HashSet<>();
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
        xmlUser.favoriteProperties = referenceProperties(user.getFavoriteProperties());
        return xmlUser;
    }

    private Set<Integer> referenceProperties(Set<Property> properties) {
        return properties.stream().map(Property::hashCode).collect(Collectors.toSet());
    }
}
