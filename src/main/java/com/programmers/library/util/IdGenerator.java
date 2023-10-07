package com.programmers.library.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
	private static final IdGenerator instance = new IdGenerator();
	private final AtomicLong id = new AtomicLong(0L);

	private IdGenerator() {
		throw new RuntimeException("싱글톤 객체이므로 new 키워드를 사용할 수 없습니다. getInstance() 메소드를 사용하세요.");
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
