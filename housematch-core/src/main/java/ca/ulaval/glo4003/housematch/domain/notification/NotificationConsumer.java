package ca.ulaval.glo4003.housematch.domain.notification;

import java.util.Collection;

public interface NotificationConsumer {

    void accept(Collection<Notification> notifications);

}
