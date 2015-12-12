package ca.ulaval.glo4003.housematch.web;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.services.notification.UserNotificationObserver;
import ca.ulaval.glo4003.housematch.services.statistics.UserStatisticsObserver;

public class ContextRefreshApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    public void onApplicationEvent(ContextRefreshedEvent event) {
        linkGlobalUserObservers(event);
    }

    private void linkGlobalUserObservers(ContextRefreshedEvent event) {
        UserRepository userRepository = event.getApplicationContext().getBean(UserRepository.class);
        UserNotificationObserver userNotificationObserver = event.getApplicationContext().getBean(UserNotificationObserver.class);
        UserStatisticsObserver userStatisticsObserver = event.getApplicationContext().getBean(UserStatisticsObserver.class);

        userRepository.getAll().forEach(u -> u.registerObserver(userNotificationObserver));
        userRepository.getAll().forEach(u -> u.registerObserver(userStatisticsObserver));
    }

}
