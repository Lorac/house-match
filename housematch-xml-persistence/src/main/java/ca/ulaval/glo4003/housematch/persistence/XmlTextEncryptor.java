package ca.ulaval.glo4003.housematch.persistence;

import org.jasypt.util.text.BasicTextEncryptor;

public class XmlTextEncryptor {

    public static final String XML_ENCRYPTION_PASSWORD = "sz98yhw";

    private BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

    public XmlTextEncryptor() {
        textEncryptor.setPassword(XML_ENCRYPTION_PASSWORD);
    }

    public String encrypt(String value) {
        return textEncryptor.encrypt(value);
    }

    public String decrypt(String value) {
        return textEncryptor.decrypt(value);
    }
}
