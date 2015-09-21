package ca.ulaval.glo4003.housematch.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailServiceLocator {

    private Properties emailProperties = new Properties();
    private Authenticator emailAuthenticator;
    protected static final String AUTH_USERNAME = "housematchb5@gmail.com";
    protected static final String AUTH_PASSWORD = "glo-4003";
    protected static final String LINK = "un lien";
    protected static final String PORT = "587";
    protected static final String HOST = "smtp.gmail.com";

    public enum EmailService {
        GMAIL;
    }

    public EmailServiceLocator(final EmailService service) {
        if (service == EmailService.GMAIL) {
            emailAuthenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(AUTH_USERNAME, AUTH_PASSWORD);
                }
            };
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");
            emailProperties.put("mail.smtp.host", HOST);
            emailProperties.put("mail.smtp.port", PORT);
        }
    }

    public Properties getEmailProperties() {
        return emailProperties;
    }

    public Authenticator getEmailAuthenticator() {
        return emailAuthenticator;
    }
}
