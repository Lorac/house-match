package ca.ulaval.glo4003.housematch.taskscheduling;

import ca.ulaval.glo4003.housematch.services.user.UserService;

public class UserLoginInactivityPolicyCheckTask implements Runnable {

    UserService userService;

    public UserLoginInactivityPolicyCheckTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run() {
        userService.applyLoginInactivityPolicy();
    }

}
