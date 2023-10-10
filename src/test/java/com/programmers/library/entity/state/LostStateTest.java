package com.programmers.library.entity.state;

import static com.programmers.library.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.programmers.library.entity.BookStateType;
import com.programmers.library.exception.BookException;

public class LostStateTest {

	private LostState lostState;

	@BeforeEach
	public void setUp() {
		lostState = new LostState();
	}

	@Test
	@DisplayName("분실 상태는 LOST 타입입니다.")
	public void testGetType() {
		assertEquals(BookStateType.LOST, lostState.getType());
	}

	@Nested
	@DisplayName("분실 상태")
	class LostStateBehaviorTest {
		@Test
		@DisplayName("대여할 경우 BOOK_LOST 예외가 발생합니다.")
		public void testBorrowThrowsException() {
			assertThrows(BookException.class, () -> lostState.borrow(), BOOK_LOST.getMessage());
		}

		@Test
		@DisplayName("반납할 경우 성공합니다.")
		public void testReturned() {
			assertDoesNotThrow(() -> lostState.returned());
		}

		@Test
		@DisplayName("분실 처리할 경우 BOOK_LOST 예외가 발생합니다.")
		public void testLostThrowsException() {
			assertThrows(BookException.class, () -> lostState.lost(), BOOK_LOST.getMessage());
		}

		@Test
		@DisplayName("정리할 경우 상태 유지합니다.")
		public void testOrganizeReturnsSameState() {
			State newState = lostState.organize();
			assertEquals(lostState, newState);
		}
	}
}
