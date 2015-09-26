package ca.ulaval.glo4003.housematch.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;

public class XmlRepositoryMarshaller extends XmlMarshaller<XmlRepositoryAssembler> {

    private XmlRepositoryAssembler xmlRepositoryAssembler;
    private ResourceLoader resourceLoader;
    private String resourceName;

    public XmlRepositoryMarshaller(final String resourceName) {
        this(new ResourceLoader(), resourceName);
    }

    public XmlRepositoryMarshaller(final ResourceLoader resourceLoader, final String resourceName) {
        super(XmlRepositoryAssembler.class);
        this.resourceLoader = resourceLoader;
        this.resourceName = resourceName;
        unmarshal();
    }

    private void unmarshal() {
        try {
            InputStream inputStream = resourceLoader.loadResourceAsInputStream(this, resourceName);
            xmlRepositoryAssembler = super.unmarshal(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("An I/O exception occured while trying to read file '%s'.", resourceName), e);
        }
    }

    public void marshal() {
        try {
            OutputStream outputStream = resourceLoader.loadResourceAsOutputStream(this, resourceName);
            super.marshal(xmlRepositoryAssembler, outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("An I/O exception occured while trying to write file '%s'.", resourceName), e);
        }
    }

    public XmlRepositoryAssembler getRepositoryAssembler() {
        return xmlRepositoryAssembler;
    }
}
