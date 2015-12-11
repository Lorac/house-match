package ca.ulaval.glo4003.housematch.persistence.property;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import ca.ulaval.glo4003.housematch.domain.property.PropertyFactory;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoRepository;

public class XmlPropertyAdapterTest {

    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.SINGLE_FAMILY_HOME;
    private static final PropertyStatus SAMPLE_PROPERTY_STATUS = PropertyStatus.FOR_SALE;
    private static final BigDecimal SAMPLE_SELLING_PRICE = BigDecimal.valueOf(523.5);
    private static final ZonedDateTime SAMPLE_DATE = ZonedDateTime.now();

    private PropertyFactory propertyFactoryMock;
    private PropertyPhotoRepository propertyPhotoRepositoryMock;
    private PropertyPhoto propertyPhotoMock;
    private Property propertyMock;
    private Address addressMock;
    private PropertyDetails propertyDetailsMock;
    private XmlProperty xmlPropertyMock;

    private XmlPropertyAdapter xmlPropertyAdapter;
    private List<BigDecimal> sellingPriceHistory = new ArrayList<>();
    private Set<PropertyPhoto> propertyPhotos = new HashSet<>();
    private Set<Integer> propertyPhotoHashCodes = new HashSet<>();

    @Before
    public void init() throws Exception {
        initMocks();
        initStubs();
        xmlPropertyAdapter = new XmlPropertyAdapter(propertyFactoryMock, propertyPhotoRepositoryMock);
    }

    private void initMocks() {
        propertyFactoryMock = mock(PropertyFactory.class);
        propertyDetailsMock = mock(PropertyDetails.class);
        propertyPhotoRepositoryMock = mock(PropertyPhotoRepository.class);
        propertyPhotoMock = mock(PropertyPhoto.class);
        addressMock = mock(Address.class);
        initPropertyMock();
        initXmlPropertyMock();
    }

    private void initPropertyMock() {
        propertyMock = mock(Property.class);
        when(propertyMock.getPropertyType()).thenReturn(SAMPLE_PROPERTY_TYPE);
        when(propertyMock.getStatus()).thenReturn(SAMPLE_PROPERTY_STATUS);
        when(propertyMock.getSellingPrice()).thenReturn(SAMPLE_SELLING_PRICE);
        when(propertyMock.getSellingPriceHistory()).thenReturn(sellingPriceHistory);
        when(propertyMock.getAddress()).thenReturn(addressMock);
        when(propertyMock.getPropertyDetails()).thenReturn(propertyDetailsMock);
        when(propertyMock.getCreationDate()).thenReturn(SAMPLE_DATE);
        when(propertyMock.getPhotos()).thenReturn(propertyPhotos);
    }

    private void initXmlPropertyMock() {
        xmlPropertyMock = mock(XmlProperty.class);
        xmlPropertyMock.propertyType = SAMPLE_PROPERTY_TYPE;
        xmlPropertyMock.status = SAMPLE_PROPERTY_STATUS;
        xmlPropertyMock.sellingPrice = SAMPLE_SELLING_PRICE;
        xmlPropertyMock.sellingPriceHistory = sellingPriceHistory;
        xmlPropertyMock.address = addressMock;
        xmlPropertyMock.propertyDetails = propertyDetailsMock;
        xmlPropertyMock.creationDate = SAMPLE_DATE;
        xmlPropertyMock.photos = propertyPhotoHashCodes;
    }

    private void initStubs() {
        when(propertyFactoryMock.createProperty(any(PropertyType.class), any(Address.class), any(BigDecimal.class)))
                .thenReturn(propertyMock);
    }

    @Test
    public void propertyAttributesAreConvertedDuringMarshalling() throws Exception {
        xmlPropertyAdapter.marshal(propertyMock);

        assertEquals(propertyMock.getPropertyType(), xmlPropertyMock.propertyType);
        assertEquals(propertyMock.getStatus(), xmlPropertyMock.status);
        assertEquals(propertyMock.getAddress(), xmlPropertyMock.address);
        assertEquals(propertyMock.getSellingPrice(), xmlPropertyMock.sellingPrice);
        assertEquals(propertyMock.getSellingPriceHistory(), xmlPropertyMock.sellingPriceHistory);
        assertEquals(propertyMock.getPropertyDetails(), xmlPropertyMock.propertyDetails);
        assertEquals(propertyMock.getCreationDate(), SAMPLE_DATE);
    }

    @Test
    public void propertyPhotosAreMarshalledAsReferenceDuringMarshalling() throws Exception {
        propertyPhotos.add(propertyPhotoMock);
        XmlProperty xmlProperty = xmlPropertyAdapter.marshal(propertyMock);
        assertThat(xmlProperty.photos, contains(propertyPhotoMock.hashCode()));
    }

    @Test
    public void xmlPropertyAttributesAreConvertedDuringUnmarshalling() throws Exception {
        xmlPropertyAdapter.unmarshal(xmlPropertyMock);

        assertEquals(xmlPropertyMock.propertyType, propertyMock.getPropertyType());
        assertEquals(xmlPropertyMock.status, propertyMock.getStatus());
        assertEquals(xmlPropertyMock.address, propertyMock.getAddress());
        assertEquals(xmlPropertyMock.sellingPrice, propertyMock.getSellingPrice());
        assertEquals(xmlPropertyMock.sellingPriceHistory, propertyMock.getSellingPriceHistory());
        assertEquals(xmlPropertyMock.propertyDetails, propertyMock.getPropertyDetails());
        assertEquals(xmlPropertyMock.creationDate, propertyMock.getCreationDate());
    }

    @Test
    public void propertyPhotosAreDereferencedDuringUnmarshalling() throws Exception {
        when(propertyPhotoRepositoryMock.getByHashCode(propertyPhotoMock.hashCode())).thenReturn(propertyPhotoMock);
        propertyPhotos.add(propertyPhotoMock);
        propertyPhotoHashCodes.add(propertyPhotoMock.hashCode());

        xmlPropertyAdapter.unmarshal(xmlPropertyMock);

        verify(propertyMock).setPhotos(eq(propertyPhotos));
    }
}
