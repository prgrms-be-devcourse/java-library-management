package util;

import java.util.TimerTask;

public interface BookScheduler {
    void scheduleBookTask(Runnable bookTask);

    default TimerTask wrap(Runnable runnable) {
        return new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
    }
}
