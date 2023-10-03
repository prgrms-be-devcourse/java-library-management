package util;

import java.util.Timer;
import java.util.TimerTask;

public class BookTaskScheduler implements BookScheduler{
    private final Timer timer = new Timer(true);

    private TimerTask wrap(Runnable runnable) {
        return new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
    }

    @Override
    public void scheduleBookTask(Runnable bookTask) {
        TimerTask timerTask = wrap(bookTask);
        timer.schedule(timerTask, 300000);
    }
}
