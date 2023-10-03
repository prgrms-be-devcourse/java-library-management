package library.book.application.utils;

import static java.util.concurrent.Executors.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class BookScheduler {

	private BookScheduler() {
	}

	private static final ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor();

	public static void registerTask(
		final Runnable task,
		final long delay
	) {
		scheduler.schedule(task, delay, TimeUnit.MINUTES);
	}
}
