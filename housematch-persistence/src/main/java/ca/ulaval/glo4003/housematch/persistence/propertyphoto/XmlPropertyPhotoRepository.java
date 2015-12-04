package ca.ulaval.glo4003.housematch.persistence.propertyphoto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoRepository;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo4003.housematch.utils.ResourceLoader;

public class XmlPropertyPhotoRepository implements PropertyPhotoRepository {

    private static final String PHOTO_DIRECTORY_NAME = "property-photos";

    private XmlRepositoryMarshaller<XmlPropertyPhotoRootElement> xmlRepositoryMarshaller;
    private XmlPropertyPhotoRootElement xmlPropertyPhotoRootElement;
    private ResourceLoader resourceLoader;

    private Map<Integer, PropertyPhoto> propertyPhotos = new ConcurrentHashMap<>();

    public XmlPropertyPhotoRepository(final XmlRepositoryMarshaller<XmlPropertyPhotoRootElement> xmlRepositoryMarshaller,
            final XmlPropertyPhotoAdapter xmlPropertyPhotoAdapter, final ResourceLoader resourceLoader) throws FileNotFoundException {
        this.resourceLoader = resourceLoader;
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository(xmlPropertyPhotoAdapter);
    }

    private void initRepository(final XmlPropertyPhotoAdapter xmlPropertyPhotoAdapter) {
        xmlRepositoryMarshaller.setMarshallingAdapters(xmlPropertyPhotoAdapter);
        xmlPropertyPhotoRootElement = xmlRepositoryMarshaller.unmarshal();

        Collection<PropertyPhoto> propertyElements = xmlPropertyPhotoRootElement.getPropertyPhotos();
        propertyElements.forEach(p -> propertyPhotos.put(p.hashCode(), p));
    }

    @Override
    public void persist(PropertyPhoto propertyPhoto, byte[] fileBytes) throws PropertyPhotoAlreadyExistsException, IOException {
        if (propertyPhotos.containsValue(propertyPhoto)) {
            throw new PropertyPhotoAlreadyExistsException(
                    String.format("A photo with hash code '%d' already exists.", propertyPhoto.hashCode()));
        }
        propertyPhotos.put(propertyPhoto.hashCode(), propertyPhoto);
        String fileName = getPhotoFileName(propertyPhoto.hashCode());
        FileUtils.writeByteArrayToFile(new File(fileName), fileBytes);
        marshal();
    }

    @Override
    public PropertyPhoto getByHashCode(Integer hashCode) throws PropertyPhotoNotFoundException {
        PropertyPhoto propertyPhoto = propertyPhotos.get(hashCode);
        if (propertyPhoto == null) {
            throw new PropertyPhotoNotFoundException(String.format("Cannot find property photo with hash code '%s'.", hashCode));
        }
        return propertyPhoto;
    }

    @Override
    public byte[] getDataByHashCode(Integer hashCode) throws PropertyPhotoNotFoundException, IOException {
        try {
            InputStream inputStream = new FileInputStream(getPhotoFileName(hashCode));
            byte[] data = IOUtils.toByteArray(inputStream);
            inputStream.close();
            return data;
        } catch (FileNotFoundException e) {
            throw new PropertyPhotoNotFoundException(String.format("Cannot find property photo with hash code '%s'.", hashCode));
        }
    }

    private String getPhotoFileName(int hashCode) throws FileNotFoundException {
        return String.format("%s/%d", getPhotoDataDirectory(), hashCode);
    }

    private String getPhotoDataDirectory() throws FileNotFoundException {
        String xmlResourceFileName = resourceLoader.getResourceFilePathFromClassPath(this, xmlRepositoryMarshaller.getResourceName());
        return String.format("%s%s", FilenameUtils.getPath(xmlResourceFileName), PHOTO_DIRECTORY_NAME);
    }

    private void marshal() {
        xmlPropertyPhotoRootElement.setProperties(propertyPhotos.values());
        xmlRepositoryMarshaller.marshal(xmlPropertyPhotoRootElement);
    }

}
