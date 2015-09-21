package ca.ulaval.glo4003.housematch.email;

import java.io.IOException;
import java.util.Properties;


public class EmailService {

    private Properties emailProperties = new Properties();

    public EmailService() {
        try {
            Properties config = new Properties();
            config.load(EmailService.class.getResourceAsStream("/mailconfig.properties"));

            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");
            emailProperties.put("mail.smtp.host", config.getProperty("host"));
            emailProperties.put("mail.smtp.port", config.getProperty("port"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getEmailProperties() {
        return emailProperties;
    }

}
