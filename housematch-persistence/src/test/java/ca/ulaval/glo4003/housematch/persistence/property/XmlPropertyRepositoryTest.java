package ca.ulaval.glo4003.housematch.persistence.property;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyStatus;
import ca.ulaval.glo4003.housematch.domain.property.PropertyType;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlPropertyRepositoryTest {
    private static final Integer SAMPLE_UNEXISTING_HASHCODE = 345435;
    private static final PropertyType SAMPLE_PROPERTY_TYPE = PropertyType.COMMERCIAL;
    private static final PropertyType ANOTHER_SAMPLE_PROPERTY_TYPE = PropertyType.LOT;
    private static final PropertyStatus SAMPLE_PROPERTY_STATUS = PropertyStatus.FOR_SALE;
    private static final PropertyStatus ANOTHER_SAMPLE_PROPERTY_STATUS = PropertyStatus.SOLD;

    private XmlRepositoryMarshaller<XmlPropertyRootElement> xmlRepositoryMarshallerMock;
    private XmlPropertyAdapter xmlPropertyAdapterMock;
    private XmlPropertyRootElement xmlPropertyRootElementMock;
    private Property propertyMock;
    private Property anotherPropertyMock;

    private XmlPropertyRepository xmlPropertyRepository;

    @Before
    public void init() {
        initMocks();
        initStubs();
        xmlPropertyRepository = new XmlPropertyRepository(xmlRepositoryMarshallerMock, xmlPropertyAdapterMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        propertyMock = mock(Property.class);
        anotherPropertyMock = mock(Property.class);
        xmlPropertyAdapterMock = mock(XmlPropertyAdapter.class);
        xmlPropertyRootElementMock = mock(XmlPropertyRootElement.class);
        xmlRepositoryMarshallerMock = mock(XmlRepositoryMarshaller.class);
    }

    private void initStubs() {
        when(xmlRepositoryMarshallerMock.unmarshal()).thenReturn(xmlPropertyRootElementMock);
        when(propertyMock.isOfType(SAMPLE_PROPERTY_TYPE)).thenReturn(true);
        when(anotherPropertyMock.isOfType(ANOTHER_SAMPLE_PROPERTY_TYPE)).thenReturn(true);
        when(propertyMock.hasStatus(SAMPLE_PROPERTY_STATUS)).thenReturn(true);
        when(anotherPropertyMock.getStatus()).thenReturn(ANOTHER_SAMPLE_PROPERTY_STATUS);
    }

    @Test
    public void persistingPropertyPersistsPropertyToRepository() throws Exception {
        xmlPropertyRepository.persist(propertyMock);

        Property property = xmlPropertyRepository.getByHashCode(propertyMock.hashCode());
        assertSame(propertyMock, property);
    }

    @Test
    public void persistingPropertyMarshallsThePropertiesInTheRepositoryMarshaller() throws Exception {
        xmlPropertyRepository.persist(propertyMock);

        verify(xmlPropertyRootElementMock).setProperties(anyCollectionOf(Property.class));
        verify(xmlRepositoryMarshallerMock).marshal(xmlPropertyRootElementMock);
    }

    @Test(expected = PropertyAlreadyExistsException.class)
    public void persistingPropertyWhichAlreadyExistsThrowsPropertyAlreadyExistsException() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        xmlPropertyRepository.persist(propertyMock);
    }

    @Test
    public void gettingPropertyByHashCodeRetrievesThePropertyFromTheSpecifiedHashCode() throws Exception {
        xmlPropertyRepository.persist(propertyMock);

        Property property = xmlPropertyRepository.getByHashCode(propertyMock.hashCode());
        assertSame(propertyMock, property);
    }

    @Test(expected = PropertyNotFoundException.class)
    public void gettingPropertyByUsernameUsingNonExistingHashCodeThrowsPropertyNotFoundException() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        xmlPropertyRepository.getByHashCode(SAMPLE_UNEXISTING_HASHCODE);
    }

    @Test
    public void updatingPropertyUpdatesPropertyToRepository() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        xmlPropertyRepository.update(propertyMock);

        verify(xmlRepositoryMarshallerMock, times(2)).marshal(xmlPropertyRootElementMock);
    }

    @Test(expected = IllegalStateException.class)
    public void updatingNonExistingPropertyThrowsIllegalStateException() throws Exception {
        xmlPropertyRepository.update(propertyMock);
    }

    @Test
    public void gettingPropertiesByStatusGetsThePropertiesByStastus() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        xmlPropertyRepository.persist(anotherPropertyMock);

        List<Property> properties = xmlPropertyRepository.getByStatus(SAMPLE_PROPERTY_STATUS);

        assertThat(properties, containsInAnyOrder(propertyMock));
    }

    @Test
    public void gettingPropertiesByTypeGetsThePropertiesByType() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        xmlPropertyRepository.persist(anotherPropertyMock);

        List<Property> properties = xmlPropertyRepository.getByType(SAMPLE_PROPERTY_TYPE);

        assertThat(properties, containsInAnyOrder(propertyMock));
    }

    @Test
    public void checkingIfRepositoryIsEmptyReturnsTrueWhenRepositoryIsEmpty() {
        assertTrue(xmlPropertyRepository.isEmpty());
    }

    @Test
    public void checkingIfRepositoryIsEmptyReturnsFalseWhenRepositoryIsNotEmpty() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        assertFalse(xmlPropertyRepository.isEmpty());
    }
}
