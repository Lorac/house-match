package ca.ulaval.glo4003.housematch.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;

public class XmlRepositoryMarshaller extends XmlMarshaller<XmlRootElementWrapper> {

    private XmlRootElementWrapper xmlRootElementWrapper;
    private File file;

    public XmlRepositoryMarshaller(final String fileResource) {
        super(XmlRootElementWrapper.class);
        setDataSource(fileResource);
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
        String filePath = null;
        try {
            URI uri = getClass().getResource(dataSource).toURI();
            filePath = uri.getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Failed to get resource URI of '%s'.", dataSource), e);
        }
        this.file = new File(filePath);
    }
}
