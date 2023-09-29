package com.programmers.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.library.entity.Book;
import com.programmers.library.exception.BookAlreadyAvailableException;
import com.programmers.library.exception.BookAlreadyBorrowedException;
import com.programmers.library.exception.BookLostException;
import com.programmers.library.exception.BookNotFoundException;
import com.programmers.library.exception.BookUnderOrganizingException;
import com.programmers.library.dto.AddBookRequest;
import com.programmers.library.dto.BorrowBookRequest;
import com.programmers.library.dto.DeleteBookRequest;
import com.programmers.library.dto.FindBookRequest;
import com.programmers.library.dto.LostBookRequest;
import com.programmers.library.dto.ReturnBookRequest;
import com.programmers.library.repository.Repository;

class LibraryManagerServiceImplTest {

	@InjectMocks
	private LibraryManagerServiceImpl service;

	@Mock
	private Repository repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddBook() {
		// given
		AddBookRequest request = new AddBookRequest("title", "author", "100");
		Book book = request.toEntity();

		// when
		service.addBook(request);

		// then
		verify(repository, times(1)).save(book);
	}

	@Test
	void getAllBooks() {
		Book book1 = new Book("title1", "author1", 100L);
		Book book2 = new Book("title2", "author2", 150L);
		when(repository.findAll()).thenReturn(Arrays.asList(book1, book2));

		List<Book> books = service.getAllBooks();

		assertEquals(2, books.size());
		verify(repository, times(1)).findAll();
	}

	@Test
	void findBooksByTitle() {
		String titleSearch = "title";
		FindBookRequest request = new FindBookRequest(titleSearch);
		Book book1 = new Book("title1", "author1", 100L);
		Book book2 = new Book("title2", "author2", 150L);
		when(repository.findByTitleLike(titleSearch)).thenReturn(Arrays.asList(book1, book2));

		List<Book> books = service.findBooksByTitle(request);

		assertEquals(2, books.size());
		verify(repository, times(1)).findByTitleLike(titleSearch);
	}

	@Test
	public void testBorrowAvailableBook() {
		String bookId = "1";
		BorrowBookRequest request = new BorrowBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		service.borrowBook(request);

		assertTrue(book.isBorrowed());
		verify(repository, times(1)).save(book);
	}

	@Test
	public void testBorrowAlreadyBorrowedBook() {
		String bookId = "1";
		BorrowBookRequest request = new BorrowBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		book.borrow();
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		assertThrows(BookAlreadyBorrowedException.class, () -> service.borrowBook(request));
	}

	@Test
	public void testBorrowLostBook() {
		String bookId = "1";
		BorrowBookRequest request = new BorrowBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		book.lost();
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		assertThrows(BookLostException.class, () -> service.borrowBook(request));
	}

	@Test
	public void testBorrowBookUnderOrganizing() {
		String bookId = "1";
		BorrowBookRequest request = new BorrowBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		book.returned();
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		assertThrows(BookUnderOrganizingException.class, () -> service.borrowBook(request));
	}

	@Test
	public void testBorrowNonexistentBook() {
		String bookId = "1";
		BorrowBookRequest request = new BorrowBookRequest(bookId);
		when(repository.findById(request.getId())).thenReturn(Optional.empty());

		assertThrows(BookNotFoundException.class, () -> service.borrowBook(request));
	}

	@Test
	public void testReturnBorrowedBook() {
		String bookId = "1";
		ReturnBookRequest request = new ReturnBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		book.borrow();
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		service.returnBook(request);

		assertTrue(book.isOrganizing());
		verify(repository, times(1)).save(book);
	}

	@Test
	public void testReturnLostBook() {
		String bookId = "1";
		ReturnBookRequest request = new ReturnBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		book.lost();
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		service.returnBook(request);

		assertTrue(book.isOrganizing());
		verify(repository, times(1)).save(book);
	}

	@Test
	public void testReturnAlreadyAvailableBook() {
		// given
		String bookId = "1";
		ReturnBookRequest request = new ReturnBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		// then
		assertThrows(BookAlreadyAvailableException.class, () -> service.returnBook(request));
	}

	@Test
	public void testReturnBookUnderOrganizing() {
		String bookId = "1";
		ReturnBookRequest request = new ReturnBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		book.returned();
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		assertThrows(BookUnderOrganizingException.class, () -> service.returnBook(request));
	}

	@Test
	public void testReturnNonexistentBook() {
		String bookId = "1";
		ReturnBookRequest request = new ReturnBookRequest(bookId);
		when(repository.findById(request.getId())).thenReturn(Optional.empty());

		assertThrows(BookNotFoundException.class, () -> service.returnBook(request));
	}

	@Test
	public void testLostAvailableBook() {
		String bookId = "1";
		LostBookRequest request = new LostBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		service.lostBook(request);

		assertTrue(book.isLost());
		verify(repository, times(1)).save(book);
	}

	@Test
	public void testLostAlreadyLostBook() {
		String bookId = "1";
		LostBookRequest request = new LostBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		book.lost();
		when(repository.findById(request.getId())).thenReturn(Optional.of(book));

		assertThrows(BookLostException.class, () -> service.lostBook(request));
	}

	@Test
	public void testLostNonexistentBook() {
		String bookId = "1";
		LostBookRequest request = new LostBookRequest(bookId);
		when(repository.findById(request.getId())).thenReturn(Optional.empty());

		assertThrows(BookNotFoundException.class, () -> service.lostBook(request));
	}

	@Test
	public void testDeleteExistingBook() {
		String bookId = "1";
		DeleteBookRequest request = new DeleteBookRequest(bookId);
		Book book = new Book("title", "author", 100L);
		book.setId(Long.parseLong(bookId));
		when(repository.findById(Long.parseLong(bookId))).thenReturn(Optional.of(book));

		service.deleteBook(request);

		verify(repository, times(1)).deleteById(Long.parseLong(bookId));
	}

	@Test
	public void testDeleteNonexistentBook() {
		String bookId = "1";
		DeleteBookRequest request = new DeleteBookRequest(bookId);
		when(repository.findById(request.getId())).thenReturn(Optional.empty());

		assertThrows(BookNotFoundException.class, () -> service.deleteBook(request));
	}
}