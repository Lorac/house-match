package ca.ulaval.glo4003.housematch.persistence.propertyphoto;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

@XmlRootElement(name = "housematch")
public class XmlPropertyPhotoRootElement {

    private Collection<PropertyPhoto> propertyPhotos = new ArrayList<>();

    @XmlElementWrapper(name = "propertyPhotos")
    @XmlElement(name = "propertyPhoto")
    @XmlJavaTypeAdapter(XmlPropertyPhotoAdapter.class)
    public Collection<PropertyPhoto> getPropertyPhotos() {
        return this.propertyPhotos;
    }

    public void setProperties(Collection<PropertyPhoto> propertyPhotos) {
        this.propertyPhotos = propertyPhotos;
    }
}
