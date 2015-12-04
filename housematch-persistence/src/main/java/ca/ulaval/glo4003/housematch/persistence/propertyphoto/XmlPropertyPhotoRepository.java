package ca.ulaval.glo4003.housematch.persistence.propertyphoto;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FilenameUtils;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoRepository;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo4003.housematch.utils.ByteArraySerializer;
import ca.ulaval.glo4003.housematch.utils.ResourceLoader;
import ca.ulaval.glo4003.housematch.utils.ThumbnailGenerator;

public class XmlPropertyPhotoRepository implements PropertyPhotoRepository {

    private static final String PHOTO_DIRECTORY_NAME = "property-photos";
    private static final String THUMBNAIL_FILENAME_FORMAT_STRING = "%s_thumbnail.jpg";
    private static final String THUMBNAIL_FILE_FORMAT_STRING = "jpg";
    private static final Dimension THUMBNAIL_DIMENSION = new Dimension(300, 200);

    private XmlRepositoryMarshaller<XmlPropertyPhotoRootElement> xmlRepositoryMarshaller;
    private XmlPropertyPhotoRootElement xmlPropertyPhotoRootElement;
    private ResourceLoader resourceLoader;
    private ByteArraySerializer byteArraySerializer;
    private ThumbnailGenerator thumbnailGenerator;

    private Map<Integer, PropertyPhoto> propertyPhotos = new ConcurrentHashMap<>();

    public XmlPropertyPhotoRepository(final XmlRepositoryMarshaller<XmlPropertyPhotoRootElement> xmlRepositoryMarshaller,
            final XmlPropertyPhotoAdapter xmlPropertyPhotoAdapter, final ResourceLoader resourceLoader,
            final ByteArraySerializer byteArraySerializer, final ThumbnailGenerator thumbnailGenerator) throws FileNotFoundException {
        this.resourceLoader = resourceLoader;
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        this.byteArraySerializer = byteArraySerializer;
        this.thumbnailGenerator = thumbnailGenerator;
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
        saveExternalPhotoFiles(getPhotoFileName(propertyPhoto.hashCode()), getThumbnailFileName(propertyPhoto.hashCode()), fileBytes);
        marshal();
    }

    private void saveExternalPhotoFiles(String fileName, String thumbnailFileName, byte[] fileBytes)
            throws FileNotFoundException, IOException {
        byteArraySerializer.serializeByteArrayToFile(fileBytes, fileName);
        thumbnailGenerator.saveThumbnail(fileName, thumbnailFileName, THUMBNAIL_DIMENSION, THUMBNAIL_FILE_FORMAT_STRING);
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
            return byteArraySerializer.unserializeFileToByteArray(getPhotoFileName(hashCode));
        } catch (FileNotFoundException e) {
            throw new PropertyPhotoNotFoundException(String.format("Cannot find property photo with hash code '%s'.", hashCode));
        }
    }

    @Override
    public byte[] getThumbnailDataByHashCode(Integer hashCode) throws PropertyPhotoNotFoundException, IOException {
        try {
            return byteArraySerializer.unserializeFileToByteArray(getThumbnailFileName(hashCode));
        } catch (FileNotFoundException e) {
            throw new PropertyPhotoNotFoundException(String.format("Cannot find property photo with hash code '%s'.", hashCode));
        }
    }

    private String getThumbnailFileName(int hashCode) throws FileNotFoundException {
        return String.format(THUMBNAIL_FILENAME_FORMAT_STRING, getPhotoFileName(hashCode));
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
