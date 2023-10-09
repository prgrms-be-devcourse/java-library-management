package com.programmers.library.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
	private static volatile IdGenerator instance;
	private final AtomicLong id = new AtomicLong(0L);

	private IdGenerator() {
	}

	public static IdGenerator getInstance() {
		if (instance == null) {
			synchronized (IdGenerator.class) {
				if (instance == null) {
					instance = new IdGenerator();
				}
			}
		}
		return instance;
	}

	public void initialize(Long initId) {
		id.set(initId);
	}

	public Long generateId() {
		return id.incrementAndGet();
	}
}
