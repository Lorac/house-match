package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoListViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoViewModel;

public class PropertyPhotoListViewModelAssemblerTest {

    private PropertyPhotoViewModelAssembler propertyPhotoViewModelAssemblerMock;
    private PropertyPhoto propertyPhotoMock;

    private Collection<PropertyPhoto> propertyPhotos = new ArrayList<>();
    private List<PropertyPhotoViewModel> propertyPhotoViewModels = new ArrayList<>();
    private PropertyPhotoListViewModelAssembler propertyPhotoListViewModelAssembler;

    @Before
    public void init() {
        initMocks();
        propertyPhotos.add(propertyPhotoMock);
        propertyPhotoListViewModelAssembler = new PropertyPhotoListViewModelAssembler(propertyPhotoViewModelAssemblerMock);
    }

    private void initMocks() {
        propertyPhotoMock = mock(PropertyPhoto.class);
        propertyPhotoViewModelAssemblerMock = mock(PropertyPhotoViewModelAssembler.class);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyPhotos() {
        when(propertyPhotoViewModelAssemblerMock.assemble(propertyPhotos)).thenReturn(propertyPhotoViewModels);
        PropertyPhotoListViewModel viewModel = propertyPhotoListViewModelAssembler.assemble(propertyPhotos);
        assertSame(viewModel.getPropertyPhotoViewModels(), propertyPhotoViewModels);
    }
}
