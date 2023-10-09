package com.programmers.library.entity.state;

import static com.programmers.library.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.programmers.library.entity.BookStateType;
import com.programmers.library.exception.BookException;

class BorrowedStateTest {
	private BorrowedState borrowedState;

	@BeforeEach
	public void setUp() {
		borrowedState = new BorrowedState();
	}

	@Test
	@DisplayName("대여중 상태는 BORROWED 타입입니다.")
	public void testGetType() {
		assertEquals(BookStateType.BORROWED, borrowedState.getType());
	}

	@Nested
	@DisplayName("대여중 상태")
	class BorrowedStateBehaviorTest {
		@Test
		@DisplayName("대여할 경우 BOOK_ALREADY_BORROWED 예외가 발생합니다.")
		public void testBorrowThrowsException() {
			assertThrows(BookException.class, () -> borrowedState.borrow(), BOOK_ALREADY_BORROWED.getMessage());
		}

		@Test
		@DisplayName("반납할 경우 성공합니다.")
		public void testReturned() {
			assertDoesNotThrow(() -> borrowedState.returned());
		}

		@Test
		@DisplayName("분실 처리할 경우 성공합니다.")
		public void testLost() {
			assertDoesNotThrow(() -> borrowedState.lost());
		}

		@Test
		@DisplayName("정리할 경우 상태 유지합니다.")
		public void testOrganizeReturnsSameState() {
			State newState = borrowedState.organize();
			assertEquals(borrowedState, newState);
		}
	}

}