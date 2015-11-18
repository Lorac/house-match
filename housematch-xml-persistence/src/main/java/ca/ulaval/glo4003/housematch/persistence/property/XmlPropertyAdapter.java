package ca.ulaval.glo4003.housematch.persistence.property;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;

public class XmlPropertyAdapter extends XmlAdapter<XmlProperty, Property> {

    private PropertyFactory propertyFactory;

    public XmlPropertyAdapter(final PropertyFactory propertyFactory) {
        this.propertyFactory = propertyFactory;
    }

    @Override
    public Property unmarshal(XmlProperty xmlProperty) throws Exception {
        Property property = propertyFactory.createProperty(xmlProperty.propertyType, xmlProperty.address, xmlProperty.sellingPrice);
        property.setPropertyDetails(xmlProperty.propertyDetails);
        property.setViewCount(xmlProperty.viewCount);
        return property;
    }

    @Override
    public XmlProperty marshal(Property property) throws Exception {
        XmlProperty xmlProperty = new XmlProperty();

        xmlProperty.propertyType = property.getPropertyType();
        xmlProperty.address = property.getAddress();
        xmlProperty.sellingPrice = property.getSellingPrice();
        xmlProperty.propertyDetails = property.getPropertyDetails();
        xmlProperty.viewCount = property.getViewCount();

        return xmlProperty;
    }
}
