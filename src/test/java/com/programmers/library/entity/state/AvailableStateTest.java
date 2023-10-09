package com.programmers.library.entity.state;

import static com.programmers.library.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.programmers.library.entity.BookStateType;
import com.programmers.library.exception.BookException;

public class AvailableStateTest {

	private AvailableState availableState;

	@BeforeEach
	public void setUp() {
		availableState = new AvailableState();
	}

	@Test
	@DisplayName("대여 가능 상태는 AVAILABLE 타입입니다.")
	public void testGetType() {
		assertEquals(BookStateType.AVAILABLE, availableState.getType());
	}

	@Nested
	@DisplayName("대여 가능 상태")
	class AvailableStateBehaviorTest {
		@Test
		@DisplayName("대여할 경우 성공합니다.")
		public void testBorrow() {
			assertDoesNotThrow(() -> availableState.borrow());
		}

		@Test
		@DisplayName("반납할 경우 BOOK_ALREADY_AVAILABLE 예외가 발생합니다.")
		public void testReturnedThrowsException() {
			assertThrows(BookException.class, () -> availableState.returned(), BOOK_ALREADY_AVAILABLE.getMessage());
		}

		@Test
		@DisplayName("분실 처리할 경우 성공합니다.")
		public void testLost() {
			assertDoesNotThrow(() -> availableState.lost());
		}

		@Test
		@DisplayName("정리할 경우 상태 유지합니다.")
		public void testOrganizeReturnsSameState() {
			State newState = availableState.organize();
			assertEquals(availableState, newState);
		}
	}
}
