package ca.ulaval.glo4003.housematch.persistence.propertyphoto;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhoto;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoAlreadyExistsException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoNotFoundException;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoRepository;
import ca.ulaval.glo4003.housematch.domain.propertyphoto.PropertyPhotoStatus;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo4003.housematch.utils.IOWrapper;
import ca.ulaval.glo4003.housematch.utils.ResourceLoader;
import ca.ulaval.glo4003.housematch.utils.ThumbnailGenerator;

public class XmlPropertyPhotoRepository implements PropertyPhotoRepository {

    private static final String PHOTO_DIRECTORY_NAME = "property-photos";
    private static final String THUMBNAIL_FILENAME_FORMAT_STRING = "%s_thumbnail.jpg";
    private static final String THUMBNAIL_FILE_FORMAT_STRING = "jpg";
    private static final Dimension THUMBNAIL_DIMENSION = new Dimension(280, 210);

    private XmlRepositoryMarshaller<XmlPropertyPhotoRootElement> xmlRepositoryMarshaller;
    private XmlPropertyPhotoRootElement xmlPropertyPhotoRootElement;
    private ResourceLoader resourceLoader;
    private IOWrapper ioWrapper;
    private ThumbnailGenerator thumbnailGenerator;

    private Map<Integer, PropertyPhoto> propertyPhotos = new ConcurrentHashMap<>();

    public XmlPropertyPhotoRepository(final XmlRepositoryMarshaller<XmlPropertyPhotoRootElement> xmlRepositoryMarshaller,
            final XmlPropertyPhotoAdapter xmlPropertyPhotoAdapter, final ResourceLoader resourceLoader, final IOWrapper ioWrapper,
            final ThumbnailGenerator thumbnailGenerator) throws FileNotFoundException {
        this.resourceLoader = resourceLoader;
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        this.ioWrapper = ioWrapper;
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
            throw new PropertyPhotoAlreadyExistsException(String.format("The specified photo already exists.", propertyPhoto.hashCode()));
        }
        propertyPhotos.put(propertyPhoto.hashCode(), propertyPhoto);
        saveExternalPhotoFiles(getPhotoFileName(propertyPhoto.hashCode()), getThumbnailFileName(propertyPhoto.hashCode()), fileBytes);
        marshal();
    }

    private void saveExternalPhotoFiles(String fileName, String thumbnailFileName, byte[] fileBytes)
            throws FileNotFoundException, IOException {
        ioWrapper.writeByteArrayToFile(fileBytes, fileName);
        thumbnailGenerator.saveThumbnail(fileName, thumbnailFileName, THUMBNAIL_DIMENSION, THUMBNAIL_FILE_FORMAT_STRING);
    }

    @Override
    public void update(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException {
        if (!propertyPhotos.containsValue(propertyPhoto)) {
            throw new IllegalStateException("Update requested for an object that is not persisted.");
        }
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
    public List<PropertyPhoto> getByStatus(PropertyPhotoStatus propertyPhotoStatus) {
        return propertyPhotos.values().stream().filter(p -> p.getStatus().equals(propertyPhotoStatus)).collect(Collectors.toList());
    }

    @Override
    public void delete(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException, IOException {
        if (!propertyPhotos.containsValue(propertyPhoto)) {
            throw new PropertyPhotoNotFoundException(String.format("Cannot find a photo with hash code '%d'.", propertyPhoto.hashCode()));
        }
        propertyPhotos.remove(propertyPhoto.hashCode());
        ioWrapper.deleteFile(getThumbnailFileName(propertyPhoto.hashCode()));
        ioWrapper.deleteFile(getPhotoFileName(propertyPhoto.hashCode()));
        marshal();
    }

    @Override
    public byte[] getThumbnailData(PropertyPhoto propertyPhoto) throws PropertyPhotoNotFoundException, IOException {
        try {
            return ioWrapper.readByteArrayFromFile(getThumbnailFileName(propertyPhoto.hashCode()));
        } catch (FileNotFoundException e) {
            throw new PropertyPhotoNotFoundException(String.format("Cannot find photo with hash code '%s'.", propertyPhoto.hashCode()));
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
        xmlPropertyPhotoRootElement.setPropertyPhotos(propertyPhotos.values());
        xmlRepositoryMarshaller.marshal(xmlPropertyPhotoRootElement);
    }
}
