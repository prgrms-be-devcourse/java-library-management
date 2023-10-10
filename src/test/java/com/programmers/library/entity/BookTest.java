package com.programmers.library.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.library.exception.BookException;

class BookTest {

	private Book book;

	@BeforeEach
	public void setUp() {
		book = BookFixture.createSampleBook();
	}

	@Test
	@DisplayName("도서를 대여합니다")
	public void testBorrowValidBook() {
		book.borrow();

		assertEquals(BookStateType.BORROWED, book.getStateType());
	}

	@Test
	@DisplayName("대여중인 도서를 대여할 경우 에러가 발생합니다")
	public void testBorrowAlreadyBorrowedBook() {
		book.borrow();

		assertThrows(BookException.class, book::borrow);
	}

	@Test
	@DisplayName("도서를 반납합니다")
	public void testReturnValidBook() {
		book.borrow();
		book.returned();

		assertEquals(BookStateType.ORGANIZING, book.getStateType());
	}

	@Test
	@DisplayName("대여 가능한 도서를 반납할 경우 에러가 발생합니다")
	public void testReturnAlreadyAvailableBook() {
		assertThrows(BookException.class, book::returned);
	}

	@Test
	@DisplayName("도서를 분실 처리합니다")
	public void testLostValidBook() {
		book.lost();

		assertEquals(BookStateType.LOST, book.getStateType());
	}

	@Test
	@DisplayName("분실 처리된 도서를 분실 처리할 경우 에러가 발생합니다")
	public void testLostAlreadyLostBook() {
		book.lost();

		assertThrows(BookException.class, book::lost);
	}

	@Test
	@DisplayName("도서를 정리합니다")
	public void testOrganizeAvailableBook() {
		book.organize();

		assertEquals(BookStateType.AVAILABLE, book.getStateType());
	}

}
