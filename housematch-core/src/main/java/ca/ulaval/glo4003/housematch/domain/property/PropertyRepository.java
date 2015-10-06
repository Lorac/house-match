package ca.ulaval.glo4003.housematch.domain.property;

public interface PropertyRepository {

    void persist(Property property) throws PropertyAlreadyExistsException;

    Property getByHashCode(Integer hashCode) throws PropertyNotFoundException;
    
    void update(Property property);
    
}
