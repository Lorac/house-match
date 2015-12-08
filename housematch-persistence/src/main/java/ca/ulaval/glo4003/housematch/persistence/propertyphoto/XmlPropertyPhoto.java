package ca.ulaval.glo4003.housematch.persistence.propertyphoto;

import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoStatus;

@XmlRootElement(name = "propertyPhoto")
public class XmlPropertyPhoto {

    public int hashCode;
    public PropertyPhotoStatus status;
    public String originalFileName;

}
