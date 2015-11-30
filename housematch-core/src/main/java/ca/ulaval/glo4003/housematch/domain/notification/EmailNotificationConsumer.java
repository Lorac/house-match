package ca.ulaval.glo4003.housematch.domain.notification;

import java.util.Collection;

import ca.ulaval.glo4003.housematch.email.MailSender;

public class EmailNotificationConsumer implements NotificationConsumer {

    private MailSender mailSender;
    private String destinationEmail;

    public EmailNotificationConsumer(final MailSender mailSender, final String destinationEmail) {
        this.destinationEmail = destinationEmail;
    }

    @Override
    public void accept(Collection<Notification> notifications) {
        String emailSubject = String.format("HouseMatch Notifications (%d)", notifications.size());
        String emailContent = buildEmailContent(notifications);
        mailSender.sendAsync(emailSubject, emailContent, destinationEmail);
    }

    private String buildEmailContent(Collection<Notification> notifications) {
        String emailContent = "";
        for (Notification notification : notifications) {
            emailContent = emailContent.concat(getFormattedNotificationDescription(notification));
        }
        return emailContent;
    }

    private String getFormattedNotificationDescription(Notification notification) {
        return String.format("%s: %s\r\n", notification.getNotificationDate().toString(), notification.getEventDescription());
    }

}
