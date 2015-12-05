package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoViewModel;

public class PropertyPhotoModelAssemblerTest {

    private PropertyPhoto propertyPhotoMock;

    private PropertyPhotoViewModelAssembler assembler;

    @Before
    public void init() {
        initMocks();
        assembler = new PropertyPhotoViewModelAssembler();
    }

    private void initMocks() {
        propertyPhotoMock = mock(PropertyPhoto.class);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyPhoto() {
        PropertyPhotoViewModel viewModel = assembler.assemble(propertyPhotoMock);
        assertEquals(propertyPhotoMock.hashCode(), viewModel.getHashCode());
    }

}
