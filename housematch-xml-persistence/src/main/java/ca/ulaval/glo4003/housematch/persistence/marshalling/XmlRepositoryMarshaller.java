package ca.ulaval.glo4003.housematch.persistence.marshalling;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jasypt.util.text.TextEncryptor;

import ca.ulaval.glo4003.housematch.domain.user.XmlUserAdapter;
import ca.ulaval.glo4003.housematch.persistence.ResourceLoader;
import ca.ulaval.glo4003.housematch.persistence.XmlRepositoryAssembler;

public class XmlRepositoryMarshaller extends XmlMarshaller<XmlRepositoryAssembler> {

    private XmlRepositoryAssembler xmlRepositoryAssembler;
    private ResourceLoader resourceLoader;
    private String resourceName;

    public XmlRepositoryMarshaller(final ResourceLoader resourceLoader, final String resourceName,
            final TextEncryptor textEncryptor) {
        super(XmlRepositoryAssembler.class);
        init(textEncryptor, resourceLoader, resourceName);
    }

    public XmlRepositoryMarshaller(final Marshaller marshaller, final Unmarshaller unmarshaller,
            final ResourceLoader resourceLoader, final String resourceName, final TextEncryptor textEncryptor) {
        super(marshaller, unmarshaller);
        init(textEncryptor, resourceLoader, resourceName);
    }

    private void init(final TextEncryptor textEncryptor, final ResourceLoader resourceLoader,
            final String resourceName) {
        this.resourceLoader = resourceLoader;
        this.resourceName = resourceName;
        initMarshallingAdapters(textEncryptor);
        unmarshal();
    }

    private void initMarshallingAdapters(final TextEncryptor textEncryptor) {
        this.marshaller.setAdapter(new XmlUserAdapter(textEncryptor));
        this.unmarshaller.setAdapter(new XmlUserAdapter(textEncryptor));
    }

    public void unmarshal() {
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
