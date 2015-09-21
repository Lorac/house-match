package ca.ulaval.glo4003.housematch.email;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailServiceLocator {

    private Properties emailProperties = new Properties();
    private Authenticator emailAuthenticator;

    public enum EmailService {
        GMAIL;
    }

    public EmailServiceLocator(final EmailService service) {
        try {
            Properties config = new Properties();
            config.load(EmailServiceLocator.class.getResourceAsStream("/gmailconfig.properties"));

            if (service == EmailService.GMAIL) {
                emailAuthenticator = new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(config.getProperty("username"),
                                config.getProperty("password"));
                    }
                };
                emailProperties.put("mail.smtp.auth", "true");
                emailProperties.put("mail.smtp.starttls.enable", "true");
                emailProperties.put("mail.smtp.host", config.getProperty("host"));
                emailProperties.put("mail.smtp.port", config.getProperty("port"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getEmailProperties() {
        return emailProperties;
    }

    public Authenticator getEmailAuthenticator() {
        return emailAuthenticator;
    }
}
