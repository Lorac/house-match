package ca.ulaval.glo4003.housematch.persistence.marshalling;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.List;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jasypt.util.text.TextEncryptor;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.XmlUserAdapter;
import ca.ulaval.glo4003.housematch.persistence.ResourceLoader;

public class XmlRepositoryMarshaller extends XmlMarshaller<XmlRootElementNode> {

    private XmlRootElementNode xmlRootElementNode;
    private ResourceLoader resourceLoader;
    private String resourceName;

    public XmlRepositoryMarshaller(final ResourceLoader resourceLoader, final String resourceName,
            final TextEncryptor textEncryptor) {
        super(XmlRootElementNode.class);
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
            xmlRootElementNode = super.unmarshal(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("An I/O exception occured while trying to read file '%s'.", resourceName), e);
        }
    }

    public void marshal() {
        try {
            OutputStream outputStream = resourceLoader.loadResourceAsOutputStream(this, resourceName);
            super.marshal(xmlRootElementNode, outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("An I/O exception occured while trying to write file '%s'.", resourceName), e);
        }
    }

    public List<User> getUsers() {
        return xmlRootElementNode.getUsers();
    }

    public void setUsers(List<User> users) {
        xmlRootElementNode.setUsers(users);
        marshal();
    }
}
