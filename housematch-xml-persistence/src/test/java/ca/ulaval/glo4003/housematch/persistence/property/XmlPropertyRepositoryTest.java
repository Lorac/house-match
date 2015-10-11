package ca.ulaval.glo4003.housematch.persistence.property;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlPropertyRepositoryTest {
    private static final Integer SAMPLE_UNEXISTING_HASHCODE = 345435;

    private XmlRepositoryMarshaller<XmlPropertyRootElement> xmlRepositoryMarshallerMock;
    private XmlPropertyRootElement xmlPropertyRootElementMock;
    private Property propertyMock;

    private XmlPropertyRepository xmlPropertyRepository;
    private List<Property> properties;

    @Before
    public void init() {
        properties = new ArrayList<Property>();
        initMocks();
        stubMethods();
        xmlPropertyRepository = new XmlPropertyRepository(xmlRepositoryMarshallerMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        propertyMock = mock(Property.class);
        xmlPropertyRootElementMock = mock(XmlPropertyRootElement.class);
        xmlRepositoryMarshallerMock = mock(XmlRepositoryMarshaller.class);
    }

    private void stubMethods() {
        when(xmlRepositoryMarshallerMock.unmarshal()).thenReturn(xmlPropertyRootElementMock);
        when(xmlPropertyRootElementMock.getProperties()).thenReturn(properties);
    }

    @Test
    public void persistingPropertyPersistsPropertyToRepository() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        assertThat(properties, contains(propertyMock));
    }

    @Test
    public void persistingPropertyMarshallsThePropertiesInTheRepositoryMarshaller() throws Exception {
        xmlPropertyRepository.persist(propertyMock);

        verify(xmlPropertyRootElementMock).setProperties(properties);
        verify(xmlRepositoryMarshallerMock).marshal(xmlPropertyRootElementMock);
    }

    @Test(expected = PropertyAlreadyExistsException.class)
    public void persistingPropertyWhichAlreadyExistsThrowsPropertyAlreadyExistsException() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        xmlPropertyRepository.persist(propertyMock);
    }

    @Test
    public void gettingPropertyByHashCodeRetrievesThePropertyByHashCode() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        assertSame(propertyMock, xmlPropertyRepository.getByHashCode(propertyMock.hashCode()));
    }

    @Test(expected = PropertyNotFoundException.class)
    public void gettingPropertyByUsernameUsingNonExistingHashCodeThrowsPropertyNotFoundException() throws Exception {
        xmlPropertyRepository.persist(propertyMock);
        xmlPropertyRepository.getByHashCode(SAMPLE_UNEXISTING_HASHCODE);
    }
    
    @Test
    public void updatingPropertyUpdatesPropertyToRepository() throws Exception{
    	 xmlPropertyRepository.persist(propertyMock);
    	 xmlPropertyRepository.update(propertyMock);

         verify(xmlRepositoryMarshallerMock, times(2)).marshal(xmlPropertyRootElementMock);
     }
    
    @Test(expected = IllegalStateException.class)
    public void updatingNonExistingPropertyThrowsIllegalStateException() throws Exception {
        xmlPropertyRepository.update(propertyMock);
    }

}
