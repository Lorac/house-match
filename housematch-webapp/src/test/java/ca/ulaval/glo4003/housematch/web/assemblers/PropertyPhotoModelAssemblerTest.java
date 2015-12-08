package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoViewModel;

public class PropertyPhotoModelAssemblerTest {

    private PropertyPhoto propertyPhotoMock;

    private List<PropertyPhoto> propertyPhotos = new ArrayList<>();
    private PropertyPhotoViewModelAssembler assembler;

    @Before
    public void init() {
        initMocks();
        propertyPhotos.add(propertyPhotoMock);
        assembler = new PropertyPhotoViewModelAssembler();
    }

    private void initMocks() {
        propertyPhotoMock = mock(PropertyPhoto.class);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyPhoto() {
        PropertyPhotoViewModel viewModel = assembler.assemble(propertyPhotoMock);
        compareViewModelToExpectedValues(viewModel);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyPhotos() {
        List<PropertyPhotoViewModel> viewModels = assembler.assemble(propertyPhotos);
        compareViewModelToExpectedValues(viewModels.get(0));
    }

    private void compareViewModelToExpectedValues(PropertyPhotoViewModel viewModel) {
        assertEquals(propertyPhotoMock.hashCode(), viewModel.getPhotoHashCode());
    }

}
