package app.library.management.core.domain.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SchedulerManager {

	private static final int POOL_SIZE = 256;
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(POOL_SIZE);

	public ScheduledExecutorService getScheduler() {
		return scheduler;
	}
}
