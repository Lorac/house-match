package ca.ulaval.glo4003.housematch.persistence;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;

public class XmlRepositoryMarshaller extends XmlMarshaller<XmlRootElementWrapper> {

    private static final Object INITIALIZATION_LOCK = new Object();

    private static XmlRepositoryMarshaller instance = null;

    private XmlRootElementWrapper xmlRootElementWrapper;
    private File file;

    public XmlRepositoryMarshaller() {
        super(XmlRootElementWrapper.class);
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
        unmarshal();
    }

    protected void marshal() {
        try {
            OutputStream outputStream = FileUtils.openOutputStream(file);
            super.marshal(xmlRootElementWrapper, outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("Failed to open file '%s' as an output stream.", file.getPath()), e);
        }
    }

    private void unmarshal() {
        try {
            InputStream inputStream = FileUtils.openInputStream(file);
            xmlRootElementWrapper = super.unmarshal(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("Failed to open file '%s' as an input stream.", file.getPath()), e);
        }
    }

    public XmlRootElementWrapper getRootElementWrapper() {
        return xmlRootElementWrapper;
    }

    public void setDataSource(String dataSource) {
        String path = getClass().getClassLoader().getResource(dataSource).getPath();
        this.file = new File(path);
    }
}
