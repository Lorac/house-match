package ca.ulaval.glo4003.housematch.domain.property;

public interface PropertyObserver {

    public void propertyStatusChanged(Property property, PropertyStatus newStatus);

}
