package ca.ulaval.glo4003.housematch.services.notification;

import java.util.List;
import java.util.Queue;

import ca.ulaval.glo4003.housematch.domain.notification.Notification;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationInterval;
import ca.ulaval.glo4003.housematch.domain.notification.NotificationType;
import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.email.MailSender;

public class NotificationService {

    private static final String NOTIFICATION_EMAIL_SUBJECT = "HouseMatch Notification";

    private MailSender mailSender;
    private UserRepository userRepository;

    public NotificationService(final MailSender mailSender, final UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    public void processQueuedUserNotification(User user, Notification notification) {
        if (user.notificationIntervalEquals(notification.getType(), NotificationInterval.IMMEDIATELY)) {
            processUserNotificationQueue(user, notification.getType());
        }
        userRepository.update(user);
    }

    public void processUserNotificationQueue(User user, NotificationType notificationType) {
        Queue<Notification> notificationQueue = user.getNotificationQueue();
        for (Notification notification : notificationQueue) {
            if (notification.getType() == notificationType) {
                sendNotificationMail(notificationQueue.remove(), user);
            }
        }
    }

    private void sendNotificationMail(Notification notification, User user) {
        mailSender.sendAsync(NOTIFICATION_EMAIL_SUBJECT, notification.toString(), user.getEmail());
    }

    public void notifyAllUsers(Notification notification) {
        List<User> users = userRepository.getAll();
        users.stream().forEach(user -> user.notify(notification));
    }

}
