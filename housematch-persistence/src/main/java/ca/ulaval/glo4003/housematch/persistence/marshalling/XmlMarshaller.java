package ca.ulaval.glo4003.housematch.persistence.marshalling;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.OutputStream;

public class XmlMarshaller<T> {

    protected Marshaller marshaller;
    protected Unmarshaller unmarshaller;
    private Class<T> type;

    public XmlMarshaller(final Class<T> type) {
        this.type = type;
        initDefaultMarshallers();
    }

    public XmlMarshaller(final Marshaller marshaller, final Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    private void initDefaultMarshallers() {
        JAXBContext jaxbContext = initializeJAXBContext();
        initDefaultMarshaller(jaxbContext);
        initDefaultUnmarshaller(jaxbContext);
    }

    private JAXBContext initializeJAXBContext() {
        try {
            return JAXBContext.newInstance(type);
        } catch (JAXBException e) {
            throw new MarshallingException("JAXB context initialization failed.", e);
        }
    }

    private void initDefaultMarshaller(JAXBContext jaxbContext) {
        try {
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException e) {
            throw new MarshallingException("Marshaller initialization failed.", e);
        }
    }

    private void initDefaultUnmarshaller(JAXBContext jaxbContext) {
        try {
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new MarshallingException("Unmarshaller initialization failed.", e);
        }
    }

    public void marshal(T element, OutputStream outputStream) {
        try {
            marshaller.marshal(element, outputStream);
        } catch (JAXBException e) {
            throw new MarshallingException("Failed to marshall objects to the specified output stream.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public T unmarshal(InputStream inputStream) {
        try {
            return (T) unmarshaller.unmarshal(inputStream);
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
