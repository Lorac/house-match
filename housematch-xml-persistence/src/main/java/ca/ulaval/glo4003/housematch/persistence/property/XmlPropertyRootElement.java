package ca.ulaval.glo4003.housematch.persistence.property;

import ca.ulaval.glo4003.housematch.domain.property.Property;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "housematch")
public class XmlPropertyRootElement {

    private List<Property> properties;

    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    public List<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
