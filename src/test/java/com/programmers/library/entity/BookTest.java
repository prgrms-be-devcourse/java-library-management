package com.programmers.library.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookTest {

	private Book book;

	@BeforeEach
	public void setUp() {
		book = new Book("Test Title", "Test Author", 200L);
	}

	@Test
	public void testInitialization() {
		assertEquals("Test Title", book.getTitle());
		assertEquals("Test Author", book.getAuthor());
		assertEquals(200L, book.getPages());
		assertTrue(book.isAvailable());
	}

	@Test
	public void testBorrow() {
		book.borrow();
		assertTrue(book.isBorrowed());
		assertFalse(book.isAvailable());
	}

	@Test
	public void testReturned() {
		book.returned();
		assertTrue(book.isOrganizing());
		assertNotNull(book.getReturnedAt());
	}

	@Test
	public void testLost() {
		book.lost();
		assertTrue(book.isLost());
	}

	@Test
	public void testFinishedOrganizingFalse() {
		book.returned();
		assertFalse(book.finishedOrganizing());
	}

	@Test
	public void testUpdateToAvailable() {
		book.updateToAvailble();
		assertTrue(book.isAvailable());
	}
}
