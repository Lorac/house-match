package ca.ulaval.glo4003.housematch.tasks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.services.user.UserService;

public class UserStatusPolicyVerificationTaskTest {

    private UserStatusPolicyVerificationTask userStatusPolicyVerificationTask;
    private UserService userServiceMock;

    @Before
    public void init() {
        userServiceMock = mock(UserService.class);
        userStatusPolicyVerificationTask = new UserStatusPolicyVerificationTask(userServiceMock);
    }

    @Test
    public void runningTheTaskCallsTheUserStatusPolicyServiceMethod() {
        userStatusPolicyVerificationTask.run();
        verify(userServiceMock).applyUserStatusPolicy();
    }
}
