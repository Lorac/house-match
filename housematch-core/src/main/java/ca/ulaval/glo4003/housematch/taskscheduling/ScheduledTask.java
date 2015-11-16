package ca.ulaval.glo4003.housematch.taskscheduling;

import java.util.Timer;
import java.util.TimerTask;

public class ScheduledTask {

    private Runnable runnableTask;
    private Long taskExecutionIntervalInMilliseconds;
    private Timer timer = new Timer();

    public ScheduledTask(Runnable runnableTask, Long taskExecutionIntervalInMilliseconds) {
        this.runnableTask = runnableTask;
        this.taskExecutionIntervalInMilliseconds = taskExecutionIntervalInMilliseconds;
    }

    public void run() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runnableTask.run();
            }
        };

        timer.schedule(timerTask, 0, taskExecutionIntervalInMilliseconds);
    }

}
