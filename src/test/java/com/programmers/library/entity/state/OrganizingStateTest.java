package com.programmers.library.entity.state;

import static com.programmers.library.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.programmers.library.entity.BookStateType;
import com.programmers.library.exception.BookException;

public class OrganizingStateTest {

	private OrganizingState organizingState;
	private LocalDateTime currentTime;

	@BeforeEach
	public void setUp() {
		currentTime = LocalDateTime.now();
		organizingState = new OrganizingState(currentTime);
	}

	@Test
	@DisplayName("도서 정리 중 상태는 ORGANIZING 타입입니다.")
	public void testGetType() {
		assertEquals(BookStateType.ORGANIZING, organizingState.getType());
	}

	@Nested
	@DisplayName("도서 정리중 상태")
	class OrganizingStateBehaviorTest {
		@Test
		@DisplayName("대여할 경우 BOOK_UNDER_ORGANIZING 예외가 발생합니다")
		public void testBorrowThrowsException() {
			assertThrows(BookException.class, () -> organizingState.borrow(), BOOK_UNDER_ORGANIZING.getMessage());
		}

		@Test
		@DisplayName("반납할 경우 BOOK_UNDER_ORGANIZING 예외가 발생합니다")
		public void testReturnedThrowsException() {
			assertThrows(BookException.class, () -> organizingState.returned(), BOOK_UNDER_ORGANIZING.getMessage());
		}

		@Test
		@DisplayName("분실 처리할 경우 성공합니다")
		public void testLost() {
			assertDoesNotThrow(() -> organizingState.lost());
		}

		@Test
		@DisplayName("반납한지 5분 미만인 상태에서 정리할 경우 상태 유지합니다.")
		public void testOrganizeReturnsSameStateBefore5Minutes() {
			State newState = organizingState.organize();
			assertEquals(organizingState, newState);
		}

		@Test
		@DisplayName("반납한지 5분 초과인 상태에서 정리할 경우 대여 가능 상태로 변경합니다.")
		public void testOrganizeReturnsAvailableStateAfter5Minutes() {
			organizingState = new OrganizingState(currentTime.minusMinutes(6));
			State newState = organizingState.organize();
			assertTrue(newState instanceof AvailableState);
		}

	}
}
