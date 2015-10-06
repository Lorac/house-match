package ca.ulaval.glo4003.housematch.persistence.property;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;

public class XmlPropertyRootElementTest {

    private XmlPropertyRootElement xmlPropertyRootElement;
    private List<Property> properties = new ArrayList<Property>();

    @Before
    public void init() {
        xmlPropertyRootElement = new XmlPropertyRootElement();
    }

    @Test
    public void setingTheUsersSetsTheUsers() {
        xmlPropertyRootElement.setProperties(properties);
        assertSame(properties, xmlPropertyRootElement.getProperties());
    }
}
