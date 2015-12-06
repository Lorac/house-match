package ca.ulaval.glo4003.housematch.web.assemblers;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoListViewModel;
import ca.ulaval.glo4003.housematch.web.viewmodels.PropertyPhotoViewModel;

public class PropertyPhotoListViewModelAssemblerTest {

    private PropertyPhotoListViewModelAssembler propertyPhotoListViewModelAssembler;
    private PropertyPhotoViewModelAssembler propertyPhotoViewModelAssemblerMock;
    private Collection<PropertyPhoto> propertyPhotos = new ArrayList<>();
    private PropertyPhotoViewModel propertyPhotoViewModelMock;
    private PropertyPhoto propertyPhotoMock;

    @Before
    public void init() {
        initMocks();
        propertyPhotos.add(propertyPhotoMock);
        propertyPhotoListViewModelAssembler = new PropertyPhotoListViewModelAssembler(propertyPhotoViewModelAssemblerMock);
    }

    private void initMocks() {
        propertyPhotoMock = mock(PropertyPhoto.class);
        propertyPhotoViewModelMock = mock(PropertyPhotoViewModel.class);
        propertyPhotoViewModelAssemblerMock = mock(PropertyPhotoViewModelAssembler.class);
    }

    @Test
    public void assemblesTheViewModelFromTheSpecifiedPropertyPhotos() {
        when(propertyPhotoViewModelAssemblerMock.assemble(propertyPhotoMock)).thenReturn(propertyPhotoViewModelMock);
        PropertyPhotoListViewModel viewModel = propertyPhotoListViewModelAssembler.assemble(propertyPhotos);
        assertThat(viewModel.getPropertyPhotoViewModels(), contains(propertyPhotoViewModelMock));
    }
}
