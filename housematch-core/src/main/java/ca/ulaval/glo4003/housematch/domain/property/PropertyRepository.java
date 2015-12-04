package ca.ulaval.glo4003.housematch.domain.property;

import java.util.List;

public interface PropertyRepository {

    void persist(Property property) throws PropertyAlreadyExistsException;

    Property getByHashCode(Integer hashCode) throws PropertyNotFoundException;

    void update(Property property);

    List<Property> getByStatus(PropertyStatus propertyStatus);

    List<Property> getByType(PropertyType propertyType);
}
