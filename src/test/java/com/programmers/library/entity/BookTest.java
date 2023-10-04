package com.programmers.library.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookTest {

	private Book book;

	@BeforeEach
	public void setUp() {
		book = new Book("Test Title", "Test Author", 200L);
	} // todo : fixture 찾아보기

	@Test
	@DisplayName("도서 엔티티를 생성합니다")
	public void testInitialization() {
		assertEquals("Test Title", book.getTitle());
		assertEquals("Test Author", book.getAuthor());
		assertEquals(200L, book.getPages());
		assertTrue(book.isAvailable());
	}

	@Test
	@DisplayName("도서를 대여합니다")
	public void testBorrow() {
		book.borrow();
		assertTrue(book.isBorrowed());
		assertFalse(book.isAvailable());
	}

	@Test
	@DisplayName("도서를 반납합니다")
	public void testReturned() {
		book.returned();
		assertTrue(book.isOrganizing());
		assertNotNull(book.getReturnedAt());
	}

	@Test
	@DisplayName("도서를 분실 처리합니다")
	public void testLost() {
		book.lost();
		assertTrue(book.isLost());
	}
}
