package ca.ulaval.glo4003.housematch.tasks;

import ca.ulaval.glo4003.housematch.services.user.UserService;

public class UserStatusPolicyVerificationTask implements Runnable {

    UserService userService;

    public UserStatusPolicyVerificationTask(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run() {
        userService.applyUserStatusPolicy();
    }

}
