package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import ca.ulaval.glo4003.housematch.domain.property.Property;


public class PropertySearchResultsViewModelAssemblerTest {

    private static final List<Property> SAMPLE_PROPERTY_LIST = new ArrayList<>();

    private PropertySearchResultsViewModelAssembler assembler;
    private Property propertyMock;
    private PropertyViewModelAssembler propertyViewModelAssemblerMock;

    @Before
    public void init() {
        assembler = mock(PropertySearchResultsViewModelAssembler.class);
        propertyMock = mock(Property.class);

        propertyViewModelAssemblerMock = mock(PropertyViewModelAssembler.class);
    }
}
