package ca.ulaval.glo4003.housematch.persistence.property;

import java.util.List;
import java.util.NoSuchElementException;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlPropertyRepository implements PropertyRepository {

    private final XmlRepositoryMarshaller<XmlPropertyRootElement> xmlRepositoryMarshaller;
    private XmlPropertyRootElement xmlPropertyRootElement;

    private List<Property> properties;

    public XmlPropertyRepository(final XmlRepositoryMarshaller<XmlPropertyRootElement> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository();
    }

    protected void initRepository() {
        xmlPropertyRootElement = xmlRepositoryMarshaller.unmarshal();
        properties = xmlPropertyRootElement.getProperties();
    }

    @Override
    public void persist(Property property) throws PropertyAlreadyExistsException {
        if (properties.stream().anyMatch(p -> p.equals(property))) {
            throw new PropertyAlreadyExistsException(
                    String.format("A property with address '%s' already exists.", property.getAddress()));
        }

        properties.add(property);
        marshal();
    }

    public Property getByHashCode(Integer hashCode) throws PropertyNotFoundException {
        try {
            return properties.stream().filter(p -> hashCode.equals(p.hashCode())).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new PropertyNotFoundException(String.format("Cannot find property with hash code '%s'.", hashCode));
        }
    }

    protected void marshal() {
        xmlPropertyRootElement.setProperties(properties);
        xmlRepositoryMarshaller.marshal(xmlPropertyRootElement);
    }

    @Override
    public void update(Property property) {
        if (!properties.contains(property)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        } else if (properties.get(properties.indexOf(property)).hashCode() != property.hashCode()) {
            throw new IllegalStateException(
                    "Object hash code has changed, which could put the repository into an invalid state.");
        }
        properties.remove(property);
        properties.add(property);
        marshal();
    }
}
