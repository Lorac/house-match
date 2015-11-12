package ca.ulaval.glo4003.housematch.persistence.property;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;

public class XmlPropertyAdapterTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);

    private PropertyFactory propertyFactoryMock;
    private Property propertyMock;
    private Address addressMock;
    private PropertyDetails propertyDetailsMock;
    private XmlProperty xmlPropertyMock;
    private ZonedDateTime sampleDate;

    private XmlPropertyAdapter xmlPropertyAdapter;

    @Before
    public void init() throws Exception {
        initMocks();
        stubMethods();
        xmlPropertyAdapter = new XmlPropertyAdapter(propertyFactoryMock);
    }

    private void initMocks() {
        propertyFactoryMock = mock(PropertyFactory.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        addressMock = mock(Address.class);
        sampleDate = ZonedDateTime.now();
        initPropertyMock();
        initXmlPropertyMock();
    }

    private void initPropertyMock() {
        propertyMock = mock(Property.class);
        when(propertyMock.getPropertyType()).thenReturn(SAMPLE_PROPERTY_TYPE);
        when(propertyMock.getSellingPrice()).thenReturn(SAMPLE_SELLING_PRICE);
        when(propertyMock.getAddress()).thenReturn(addressMock);
        when(propertyMock.getPropertyDetails()).thenReturn(propertyDetailsMock);
        when(propertyMock.getDate()).thenReturn(sampleDate);
    }

    private void initXmlPropertyMock() {
        xmlPropertyMock = mock(XmlProperty.class);
        xmlPropertyMock.propertyType = SAMPLE_PROPERTY_TYPE;
        xmlPropertyMock.sellingPrice = SAMPLE_SELLING_PRICE;
        xmlPropertyMock.address = addressMock;
        xmlPropertyMock.propertyDetails = propertyDetailsMock;
        xmlPropertyMock.date = sampleDate.toString();
    }

    private void stubMethods() {
        when(propertyFactoryMock.createProperty(any(PropertyType.class), any(Address.class), any(BigDecimal.class)))
                .thenReturn(propertyMock);
    }

    @Test
    public void propertyAttributesAreConvertedDuringMarshalling() throws Exception {
        xmlPropertyAdapter.marshal(propertyMock);

        assertEquals(propertyMock.getPropertyType(), xmlPropertyMock.propertyType);
        assertEquals(propertyMock.getAddress(), xmlPropertyMock.address);
        assertEquals(propertyMock.getSellingPrice(), xmlPropertyMock.sellingPrice);
        assertEquals(propertyMock.getPropertyDetails(), xmlPropertyMock.propertyDetails);
        assertEquals(propertyMock.getDate(), sampleDate);
    }

    @Test
    public void xmlPropertyAttributesAreConvertedDuringUnmarshalling() throws Exception {
        xmlPropertyAdapter.unmarshal(xmlPropertyMock);

        assertEquals(xmlPropertyMock.propertyType, propertyMock.getPropertyType());
        assertEquals(xmlPropertyMock.address, propertyMock.getAddress());
        assertEquals(xmlPropertyMock.sellingPrice, propertyMock.getSellingPrice());
        assertEquals(xmlPropertyMock.propertyDetails, propertyMock.getPropertyDetails());
        assertEquals(xmlPropertyMock.date, propertyMock.getDate().toString());
    }
}
