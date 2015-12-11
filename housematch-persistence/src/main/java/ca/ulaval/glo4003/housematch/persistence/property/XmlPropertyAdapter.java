package ca.ulaval.glo4003.housematch.persistence.property;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoRepository;

public class XmlPropertyAdapter extends XmlAdapter<XmlProperty, Property> {

    private PropertyFactory propertyFactory;
    private PropertyPhotoRepository propertyPhotoRepository;

    public XmlPropertyAdapter(final PropertyFactory propertyFactory, final PropertyPhotoRepository propertyPhotoRepository) {
        this.propertyFactory = propertyFactory;
        this.propertyPhotoRepository = propertyPhotoRepository;
    }

    @Override
    public Property unmarshal(XmlProperty xmlProperty) throws Exception {
        Property property = propertyFactory.createProperty(xmlProperty.propertyType, xmlProperty.address, xmlProperty.sellingPrice);
        property.setSellingPriceHistory(xmlProperty.sellingPriceHistory);
        property.setPropertyDetails(xmlProperty.propertyDetails);
        property.setCreationDate(xmlProperty.creationDate);
        property.setViewCount(xmlProperty.viewCount);
        property.setStatus(xmlProperty.status);
        property.setPhotos(dereferencePropertyPhotos(xmlProperty.photos));
        return property;
    }

    private Set<PropertyPhoto> dereferencePropertyPhotos(Set<Integer> propertyPhotoHashCodes) throws PropertyPhotoNotFoundException {
        Set<PropertyPhoto> propertyPhotos = new HashSet<>();
        for (Integer propertyPhotoHashCode : propertyPhotoHashCodes) {
            propertyPhotos.add(propertyPhotoRepository.getByHashCode(propertyPhotoHashCode));
        }
        return propertyPhotos;
    }

    @Override
    public XmlProperty marshal(Property property) throws Exception {
        XmlProperty xmlProperty = new XmlProperty();

        xmlProperty.propertyType = property.getPropertyType();
        xmlProperty.address = property.getAddress();
        xmlProperty.sellingPrice = property.getSellingPrice();
        xmlProperty.sellingPriceHistory = property.getSellingPriceHistory();
        xmlProperty.propertyDetails = property.getPropertyDetails();
        xmlProperty.creationDate = property.getCreationDate();
        xmlProperty.viewCount = property.getViewCount();
        xmlProperty.status = property.getStatus();
        xmlProperty.photos = referencePropertyPhotos(property.getPhotos());

        return xmlProperty;
    }

    private Set<Integer> referencePropertyPhotos(Set<PropertyPhoto> propertyPhotos) {
        return propertyPhotos.stream().map(PropertyPhoto::hashCode).collect(Collectors.toSet());
    }
}
