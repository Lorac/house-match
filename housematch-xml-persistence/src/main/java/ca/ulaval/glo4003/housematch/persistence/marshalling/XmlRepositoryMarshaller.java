package ca.ulaval.glo4003.housematch.persistence.marshalling;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import ca.ulaval.glo4003.housematch.utils.ResourceLoader;

public class XmlRepositoryMarshaller<T> extends XmlMarshaller<T> {

    private static final Object XML_MARSHALL_LOCK = new Object();

    private ResourceLoader resourceLoader;
    private String resourceName;

    public XmlRepositoryMarshaller(final Class<T> type, final ResourceLoader resourceLoader,
            final String resourceName) {
        super(type);
        init(resourceLoader, resourceName);
    }

    public XmlRepositoryMarshaller(final Marshaller marshaller, final Unmarshaller unmarshaller,
            final ResourceLoader resourceLoader, final String resourceName) {
        super(marshaller, unmarshaller);
        init(resourceLoader, resourceName);
    }

    private void init(final ResourceLoader resourceLoader, final String resourceName) {
        this.resourceLoader = resourceLoader;
        this.resourceName = resourceName;
    }

    @SuppressWarnings("rawtypes")
    public void setMarshallingAdapters(XmlAdapter xmlAdapter) {
        marshaller.setAdapter(xmlAdapter);
        unmarshaller.setAdapter(xmlAdapter);
    }

    public T unmarshal() {
        T unmarshalledObject;
        try {
            synchronized (XML_MARSHALL_LOCK) {
                InputStream inputStream = resourceLoader.loadResourceAsInputStream(this, resourceName);
                unmarshalledObject = super.unmarshal(inputStream);
                inputStream.close();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("An I/O exception occured while trying to read file '%s'.", resourceName), e);
        }
        return unmarshalledObject;
    }

    public void marshal(T object) {
        try {
            synchronized (XML_MARSHALL_LOCK) {
                OutputStream outputStream = resourceLoader.loadResourceAsOutputStream(this, resourceName);
                super.marshal(object, outputStream);
                outputStream.close();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("An I/O exception occured while trying to write file '%s'.", resourceName), e);
        }
    }
}
