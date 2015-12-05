package ca.ulaval.glo4003.housematch.persistence.propertyphoto;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;

public class XmlPropertyPhotoRootElementTest {

    private XmlPropertyPhotoRootElement xmlPropertyPhotoRootElement;
    private List<PropertyPhoto> photos = new ArrayList<>();

    @Before
    public void init() {
        xmlPropertyPhotoRootElement = new XmlPropertyPhotoRootElement();
    }

    @Test
    public void setingThePropertiesSetsTheProperties() {
        xmlPropertyPhotoRootElement.setPropertyPhotos(photos);

        Collection<PropertyPhoto> returnedPhotos = xmlPropertyPhotoRootElement.getPropertyPhotos();
        assertSame(photos, returnedPhotos);
    }
}
