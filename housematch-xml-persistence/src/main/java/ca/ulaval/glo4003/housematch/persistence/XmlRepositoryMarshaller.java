package ca.ulaval.glo4003.housematch.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import org.jasypt.util.text.BasicTextEncryptor;

import ca.ulaval.glo4003.housematch.domain.user.XmlUserAdapter;

public class XmlRepositoryMarshaller extends XmlMarshaller<XmlRepositoryAssembler> {

    private XmlRepositoryAssembler xmlRepositoryAssembler;
    private ResourceLoader resourceLoader;
    private String resourceName;

    public XmlRepositoryMarshaller(final ResourceLoader resourceLoader, final String resourceName,
            final BasicTextEncryptor basicTextEncryptor, final String encryptionPassword) {
        super(XmlRepositoryAssembler.class);
        this.resourceLoader = resourceLoader;
        this.resourceName = resourceName;
        initMarshallingAdapters(basicTextEncryptor, encryptionPassword);
        unmarshal();
    }

    private void initMarshallingAdapters(final BasicTextEncryptor basicTextEncryptor, final String encryptionPassword) {
        basicTextEncryptor.setPassword(encryptionPassword);
        this.marshaller.setAdapter(new XmlUserAdapter(basicTextEncryptor));
        this.unmarshaller.setAdapter(new XmlUserAdapter(basicTextEncryptor));
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
