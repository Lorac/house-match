package ca.ulaval.glo4003.housematch.taskscheduling;

import java.util.List;

public class ScheduledTaskExecutor {

    private List<ScheduledTask> scheduledTasks;

    public void registerTask(ScheduledTask scheduledTask) {
        scheduledTasks.add(scheduledTask);
        scheduledTask.run();
    }
}
