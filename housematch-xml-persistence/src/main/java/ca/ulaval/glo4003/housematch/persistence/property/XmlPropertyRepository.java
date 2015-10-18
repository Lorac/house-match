package ca.ulaval.glo4003.housematch.persistence.property;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlPropertyRepository implements PropertyRepository {

    private final XmlRepositoryMarshaller<XmlPropertyRootElement> xmlRepositoryMarshaller;
    private XmlPropertyRootElement xmlPropertyRootElement;

    private Map<Integer, Property> properties = new ConcurrentHashMap<>();

    public XmlPropertyRepository(final XmlRepositoryMarshaller<XmlPropertyRootElement> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository();
    }

    private void initRepository() {
        xmlPropertyRootElement = xmlRepositoryMarshaller.unmarshal();

        Collection<Property> propertyElements = xmlPropertyRootElement.getProperties();
        propertyElements.forEach(p -> properties.put(p.hashCode(), p));
    }

    @Override
    public void persist(Property property) throws PropertyAlreadyExistsException {
        if (properties.containsValue(property)) {
            throw new PropertyAlreadyExistsException(String.format("A property with address '%s' already exists.", property.getAddress()));
        }

        properties.put(property.hashCode(), property);
        marshal();
    }

    @Override
    public Property getByHashCode(Integer hashCode) throws PropertyNotFoundException {
        Property property = properties.get(hashCode);
        if (property == null) {
            throw new PropertyNotFoundException(String.format("Cannot find property with hash code '%s'.", hashCode));
        }
        return property;
    }

    @Override
    public void update(Property property) {
        if (!properties.containsValue(property)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        }

        marshal();
    }

    @Override
    public List<Property> getAll() {
        return properties;
    }

    private void marshal() {
        xmlPropertyRootElement.setProperties(properties);
        xmlRepositoryMarshaller.marshal(xmlPropertyRootElement);
    }
}
