package ca.ulaval.glo4003.housematch.persistence;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlMarshaller<T> {

    private static final Object XML_MARSHALL_LOCK = new Object();

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private Class<T> type;

    public XmlMarshaller(final Class<T> type) {
        this.type = type;
        initDefaultMarshallers();
    }

    private void initDefaultMarshallers() {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(type);
        } catch (JAXBException e) {
            throw new MarshallingException("JAXB context initialization failed.", e);
        }
        try {
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException e) {
            throw new MarshallingException("Marshaller initialization failed.", e);
        }
        try {
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new MarshallingException("Unmarshaller initialization failed.", e);
        }
    }

    public XmlMarshaller(final Marshaller marshaller, final Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    public void marshal(T element, OutputStream outputStream) {
        try {
            synchronized (XML_MARSHALL_LOCK) {
                marshaller.marshal(element, outputStream);
            }
        } catch (JAXBException e) {
            throw new MarshallingException("Failed to marshall objects to the specified output stream.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public T unmarshal(InputStream inputStream) {
        try {
            synchronized (XML_MARSHALL_LOCK) {
                return (T) unmarshaller.unmarshal(inputStream);
            }
        } catch (JAXBException e) {
            throw new MarshallingException("Failed to unmarshall objects from the specified input stream.", e);
        }
    }

    public Marshaller getMarshaller() {
        return marshaller;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }
}
