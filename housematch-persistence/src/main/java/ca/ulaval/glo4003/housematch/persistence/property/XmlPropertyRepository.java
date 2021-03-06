package ca.ulaval.glo4003.housematch.persistence.property;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyRepository;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlPropertyRepository implements PropertyRepository {

    private XmlRepositoryMarshaller<XmlPropertyRootElement> xmlRepositoryMarshaller;
    private XmlPropertyRootElement xmlPropertyRootElement;

    private Map<Integer, Property> properties = new ConcurrentHashMap<>();

    public XmlPropertyRepository(final XmlRepositoryMarshaller<XmlPropertyRootElement> xmlRepositoryMarshaller,
            final XmlPropertyAdapter xmlPropertyAdapter) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository(xmlPropertyAdapter);
    }

    private void initRepository(final XmlPropertyAdapter xmlPropertyAdapter) {
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlPropertyAdapter);
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
    public List<Property> getByStatus(PropertyStatus propertyStatus) {
        return properties.values().stream().filter(p -> p.hasStatus(propertyStatus)).collect(Collectors.toList());
    }

    @Override
    public List<Property> getByType(PropertyType propertyType) {
        return properties.values().stream().filter(p -> p.isOfType(propertyType)).collect(Collectors.toList());
    }

    @Override
    public Boolean isEmpty() {
        return properties.isEmpty();
    }

    private void marshal() {
        xmlPropertyRootElement.setProperties(properties.values());
        xmlRepositoryMarshaller.marshal(xmlPropertyRootElement);
    }

}
