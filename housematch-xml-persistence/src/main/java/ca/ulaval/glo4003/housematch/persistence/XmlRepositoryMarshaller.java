package ca.ulaval.glo4003.housematch.persistence;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URISyntaxException;

public class XmlRepositoryMarshaller {

    private static final String XML_RESOURCE_FILE_PATH = "/housematch-data.xml";
    private static final Object XML_MARSHALL_LOCK = new Object();
    private static final Object INITIALIZATION_LOCK = new Object();

    private static XmlRepositoryMarshaller instance = null;

    private File file;
    private XmlRootNodeAssembler xmlRootNode;

    protected XmlRepositoryMarshaller() {
        String filePath = null;
        try {
            filePath = getClass().getResource(XML_RESOURCE_FILE_PATH).toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException(String.format("File '%s' was not found.", file.getPath()));
        }

        unmarshall();
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

    public XmlRootNodeAssembler getRootNode() {
        return xmlRootNode;
    }

    public void marshall() {
        try {
            synchronized (XML_MARSHALL_LOCK) {
                JAXBContext jaxbContext = JAXBContext.newInstance(XmlRootNodeAssembler.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.marshal(xmlRootNode, file);
            }
        } catch (JAXBException e) {
            throw new MarshallingException("Failed to marshall objects to XML repository.", e);
        }
    }

    public void unmarshall() {
        try {
            synchronized (XML_MARSHALL_LOCK) {
                JAXBContext jaxbContext = JAXBContext.newInstance(XmlRootNodeAssembler.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                xmlRootNode = (XmlRootNodeAssembler) jaxbUnmarshaller.unmarshal(file);
            }
        } catch (JAXBException e) {
            throw new MarshallingException("Failed to unmarshall objects from XML repository.", e);
        }
    }
}
