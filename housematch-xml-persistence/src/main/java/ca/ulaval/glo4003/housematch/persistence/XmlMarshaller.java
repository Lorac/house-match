package ca.ulaval.glo4003.housematch.persistence;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlMarshaller {

    private static final Object XML_MARSHALL_LOCK = new Object();

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private XmlRootNodeAssembler xmlRootNodeAssembler;

    public XmlMarshaller() {
        initDefaultMarshallers();
    }

    private void initDefaultMarshallers() {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(XmlRootNodeAssembler.class);
        } catch (JAXBException e) {
            throw new MarshallingException("JAXB context initialization failed.", e);
        }
        try {
            marshaller = jaxbContext.createMarshaller();
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

    public XmlRootNodeAssembler getRootNodeAssembler() {
        return xmlRootNodeAssembler;
    }

    public void marshall(OutputStream outputStream) {
        try {
            synchronized (XML_MARSHALL_LOCK) {
                marshaller.marshal(xmlRootNodeAssembler, outputStream);
            }
        } catch (JAXBException e) {
            throw new MarshallingException("Failed to marshall objects to XML repository.", e);
        }
    }

    public void unmarshall(InputStream inputStream) {
        try {
            synchronized (XML_MARSHALL_LOCK) {
                xmlRootNodeAssembler = (XmlRootNodeAssembler) unmarshaller.unmarshal(inputStream);
            }
        } catch (JAXBException e) {
            throw new MarshallingException("Failed to unmarshall objects from XML repository.", e);
        }
    }
}
