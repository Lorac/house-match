package ca.ulaval.glo4003.housematch.persistence.property;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlPropertyAdapter extends XmlAdapter<XmlProperty, Property> {

    private PropertyFactory propertyFactory;

    public XmlPropertyAdapter(final PropertyFactory propertyFactory) {
        this.propertyFactory = propertyFactory;
    }

    @Override
    public Property unmarshal(XmlProperty xmlProperty) throws Exception {
        Property property = propertyFactory.createProperty(xmlProperty.propertyType, xmlProperty.address, xmlProperty.sellingPrice);
        property.setPropertyDetails(xmlProperty.propertyDetails);
        property.setCreationDate(xmlProperty.creationDate);
        property.setViewCount(xmlProperty.viewCount);
        property.setStatus(xmlProperty.status);
        return property;
    }

    @Override
    public XmlProperty marshal(Property property) throws Exception {
        XmlProperty xmlProperty = new XmlProperty();

        xmlProperty.propertyType = property.getPropertyType();
        xmlProperty.address = property.getAddress();
        xmlProperty.sellingPrice = property.getSellingPrice();
        xmlProperty.propertyDetails = property.getPropertyDetails();
        xmlProperty.creationDate = property.getCreationDate();
        xmlProperty.viewCount = property.getViewCount();
        xmlProperty.status = property.getStatus();

        return xmlProperty;
    }
}