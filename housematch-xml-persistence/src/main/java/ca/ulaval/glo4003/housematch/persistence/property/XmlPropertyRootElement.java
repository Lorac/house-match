package ca.ulaval.glo4003.housematch.persistence.property;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.housematch.domain.property.Property;

@XmlRootElement(name = "housematch")
public class XmlPropertyRootElement {

    private Collection<Property> properties;

    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    public Collection<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(Collection<Property> properties) {
        this.properties = properties;
    }
}
