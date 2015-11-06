package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.HitCount;

public class PropertyHitCount extends HitCount {

    private Property property;

    public PropertyHitCount(Property property) {
        this.property = property;
    }

    public boolean hasProperty(Property property) {
        return this.property.equals(property);
    }
}
