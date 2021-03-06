package ca.ulaval.glo4003.housematch.persistence.propertyphoto;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoFactory;

public class XmlPropertyPhotoAdapter extends XmlAdapter<XmlPropertyPhoto, PropertyPhoto> {

    private PropertyPhotoFactory propertyPhotoFactory;

    public XmlPropertyPhotoAdapter(final PropertyPhotoFactory propertyPhotoFactory) {
        this.propertyPhotoFactory = new PropertyPhotoFactory();
    }

    @Override
    public PropertyPhoto unmarshal(XmlPropertyPhoto xmlPropertyPhoto) throws Exception {
        PropertyPhoto propertyPhoto =
                propertyPhotoFactory.createPropertyPhoto(xmlPropertyPhoto.hashCode, xmlPropertyPhoto.originalFileName);
        propertyPhoto.setStatus(xmlPropertyPhoto.status);
        return propertyPhoto;
    }

    @Override
    public XmlPropertyPhoto marshal(PropertyPhoto propertyPhoto) throws Exception {
        XmlPropertyPhoto xmlPropertyPhoto = new XmlPropertyPhoto();
        xmlPropertyPhoto.hashCode = propertyPhoto.hashCode();
        xmlPropertyPhoto.status = propertyPhoto.getStatus();
        xmlPropertyPhoto.originalFileName = propertyPhoto.getOriginalFileName();
        return xmlPropertyPhoto;
    }
}
