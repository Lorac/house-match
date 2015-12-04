package ca.ulaval.glo4003.housematch.domain.property;

public interface PropertyObserver {

    void propertyStatusChanged(Property property, PropertyStatus newStatus);

    void propertyDetailsChanged(Property property, PropertyDetails newPropertyDetails);

}
