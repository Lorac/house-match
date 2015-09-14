package ca.ulaval.glo4003.housematch.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;

public class XmlRepositoryMarshaller {

    private static final String XML_RESOURCE_FILE_PATH = "/housematch-data.xml";
    private static final Object INITIALIZATION_LOCK = new Object();

    private static XmlRepositoryMarshaller instance = null;

    private XmlMarshaller xmlMarshaller;
    private File file;

    public XmlRepositoryMarshaller() {
        this.xmlMarshaller = new XmlMarshaller();
        initRepository();
    }

    public XmlRepositoryMarshaller(final XmlMarshaller xmlMarchaller) {
        this.xmlMarshaller = xmlMarchaller;
        initRepository();
    }

    public static synchronized XmlRepositoryMarshaller getInstance() {
        if (instance == null) {
            synchronized (INITIALIZATION_LOCK) {
                if (instance == null) {
                    instance = new XmlRepositoryMarshaller();
                }
            }
        }

        return instance;
    }

    protected void initRepository() {
        openXmlResourceFile();
        unmarshall();
    }

    public XmlRootNodeAssembler getRootNodeAssembler() {
        return xmlMarshaller.getRootNodeAssembler();
    }

    protected void marshall() {
        try {
            OutputStream outputStream = FileUtils.openOutputStream(file);
            xmlMarshaller.marshall(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("Failed to open file '%s' as an output stream.", file.getPath()), e);
        }
    }

    private void unmarshall() {
        try {
            InputStream inputStream = FileUtils.openInputStream(file);
            xmlMarshaller.unmarshall(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("Failed to open file '%s' as an input stream.", file.getPath()), e);
        }
    }

    private void openXmlResourceFile() {
        String filePath = null;
        try {
            filePath = getClass().getResource(XML_RESOURCE_FILE_PATH).toURI().getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Failed to get resource URI of '%s'.", XML_RESOURCE_FILE_PATH), e);
        }
        file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException(String.format("File '%s' was not found.", file.getPath()));
        }
    }
}
