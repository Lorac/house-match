package ca.ulaval.glo4003.housematch.persistence.property;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.ulaval.glo4003.housematch.domain.property.Property;

@XmlRootElement(name = "housematch")
public class XmlPropertyRootElement {

    private Collection<Property> properties = new ArrayList<>();

    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    @XmlJavaTypeAdapter(XmlPropertyAdapter.class)
    public Collection<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(Collection<Property> properties) {
        this.properties = properties;
    }
}
