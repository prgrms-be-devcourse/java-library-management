package com.programmers.library.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
	private static final IdGenerator instance = new IdGenerator();
	private final AtomicLong id = new AtomicLong(0L);

	private IdGenerator() {
	}

	public static IdGenerator getInstance() {
		return instance;
	}

	public void initialize(Long initId) {
		id.set(initId);
	}

	public Long generateId() {
		return id.incrementAndGet();
	}
}
