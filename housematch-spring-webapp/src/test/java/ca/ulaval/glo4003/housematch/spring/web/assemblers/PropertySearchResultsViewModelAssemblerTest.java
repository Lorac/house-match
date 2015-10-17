package ca.ulaval.glo4003.housematch.spring.web.assemblers;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.spring.web.viewmodels.PropertySearchResultsViewModel;

public class PropertySearchResultsViewModelAssemblerTest {

    private static final List<Property> SAMPLE_PROPERTY_LIST = new ArrayList<Property>();

    private PropertySearchResultsViewModelAssembler assembler;

    @Before
    public void init() {
        assembler = new PropertySearchResultsViewModelAssembler();
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyList() {
        PropertySearchResultsViewModel viewModel = assembler.assembleFromPropertyList(SAMPLE_PROPERTY_LIST);
        assertSame(SAMPLE_PROPERTY_LIST, viewModel.getProperties());
    }
}