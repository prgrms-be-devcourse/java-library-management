package com.programmers.library.service;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.library.dto.AddBookRequestDto;
import com.programmers.library.dto.BorrowBookRequestDto;
import com.programmers.library.dto.DeleteBookRequestDto;
import com.programmers.library.dto.FindBookRequestDto;
import com.programmers.library.dto.LostBookRequestDto;
import com.programmers.library.dto.ReturnBookRequestDto;
import com.programmers.library.entity.Book;
import com.programmers.library.exception.BookException;
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
	@DisplayName("도서를 등록합니다")
	public void testAddBook() {
		// given
		AddBookRequestDto request = new AddBookRequestDto("title", "author", "100");
		Book book = request.toEntity();

		// when
		service.addBook(request);

		// then
		verify(repository, times(1)).save(book);
	}

	@Test
	@DisplayName("전체 도서를 조회합니다")
	void getAllBooks() {
		Book book1 = new Book("title1", "author1", 100L);
		Book book2 = new Book("title2", "author2", 150L);
		when(repository.findAll()).thenReturn(Arrays.asList(book1, book2));

		List<Book> books = service.getAllBooks();

		assertEquals(2, books.size());
		verify(repository, times(1)).findAll();
	}

	@Test
	@DisplayName("제목으로 도서를 조회합니다")
	void findBooksByTitle() {
		String titleSearch = "title";
		FindBookRequestDto request = new FindBookRequestDto(titleSearch);
		Book book1 = new Book("title1", "author1", 100L);
		Book book2 = new Book("title2", "author2", 150L);
		when(repository.findByTitleLike(titleSearch)).thenReturn(Arrays.asList(book1, book2));

		List<Book> books = service.findBooksByTitle(request);

		assertEquals(2, books.size());
		verify(repository, times(1)).findByTitleLike(titleSearch);
	}

	@Nested
	@DisplayName("도서를 반납합니다")
	class ReturnBookTest {
		@Test
		@DisplayName("도서를 정상적으로 반납합니다")
		public void testReturnBorrowedBook() {
			String bookId = "1";
			ReturnBookRequestDto request = new ReturnBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			book.borrow();
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			service.returnBook(request);

			assertTrue(book.isOrganizing());
			verify(repository, times(1)).save(book);
		}

		@Test
		@DisplayName("분실된 도서를 반납시 예외가 발생합니다")
		public void testReturnLostBook() {
			String bookId = "1";
			ReturnBookRequestDto request = new ReturnBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			book.lost();
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			service.returnBook(request);

			assertTrue(book.isOrganizing());
			verify(repository, times(1)).save(book);
		}

		@Test
		@DisplayName("대여 가능한 도서를 반납시 예외가 발생합니다")
		public void testReturnAlreadyAvailableBook() {
			String bookId = "1";
			ReturnBookRequestDto request = new ReturnBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			assertThrows(BookException.class, () -> service.returnBook(request), BOOK_ALREADY_AVAILABLE);
		}

		@Test
		@DisplayName("정리중인 도서를 반납시 예외가 발생합니다")
		public void testReturnBookUnderOrganizing() {
			String bookId = "1";
			ReturnBookRequestDto request = new ReturnBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			book.returned();
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			assertThrows(BookException.class, () -> service.returnBook(request), BOOK_UNDER_ORGANIZING);
		}

		@Test
		@DisplayName("존재하지 않는 도서를 반납시 예외가 발생합니다")
		public void testReturnNonExistentBook() {
			String bookId = "1";
			ReturnBookRequestDto request = new ReturnBookRequestDto(bookId);
			when(repository.findById(request.getId())).thenReturn(Optional.empty());

			assertThrows(BookException.class, () -> service.returnBook(request), BOOK_NOT_FOUND);
		}
	}


	@Nested
	@DisplayName("도서를 분실 처리합니다")
	class LostBookTest {
		@Test
		@DisplayName("도서를 정상적으로 분실 처리합니다")

		public void testLostAvailableBook() {
			String bookId = "1";
			LostBookRequestDto request = new LostBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			service.lostBook(request);

			assertTrue(book.isLost());
			verify(repository, times(1)).save(book);
		}

		@Test
		@DisplayName("분실된 도서를 분실 처리시 예외가 발생합니다")
		public void testLostAlreadyLostBook() {
			String bookId = "1";
			LostBookRequestDto request = new LostBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			book.lost();
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			assertThrows(BookException.class, () -> service.lostBook(request), BOOK_LOST);
		}

		@Test
		@DisplayName("존재하지 않는 도서를 분실 처리시 예외가 발생합니다")
		public void testLostNonExistentBook() {
			String bookId = "1";
			LostBookRequestDto request = new LostBookRequestDto(bookId);
			when(repository.findById(request.getId())).thenReturn(Optional.empty());

			assertThrows(BookException.class, () -> service.lostBook(request), BOOK_NOT_FOUND);
		}
	}

	@Nested
	@DisplayName("도서를 삭제 처리합니다")
	class DeleteBookTest {
		@Test
		@DisplayName("도서를 정상적으로 삭제합니다")
		public void testDeleteExistingBook() {
			String bookId = "1";
			DeleteBookRequestDto request = new DeleteBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			book.setId(Long.parseLong(bookId));
			when(repository.findById(Long.parseLong(bookId))).thenReturn(Optional.of(book));

			service.deleteBook(request);

			verify(repository, times(1)).deleteById(Long.parseLong(bookId));
		}

		@Test
		@DisplayName("존재하지 않는 도서를 삭제시 예외가 발생합니다")
		public void testDeleteNonExistentBook() {
			String bookId = "1";
			DeleteBookRequestDto request = new DeleteBookRequestDto(bookId);
			when(repository.findById(request.getId())).thenReturn(Optional.empty());

			assertThrows(BookException.class, () -> service.deleteBook(request), BOOK_NOT_FOUND);
		}
	}

	@Nested
	@DisplayName("도서를 대여합니다")
	class BorrowBookTest {
		@Test
		@DisplayName("도서를 정상적으로 대여합니다")
		public void testBorrowAvailableBook() {
			String bookId = "1";
			BorrowBookRequestDto request = new BorrowBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			service.borrowBook(request);

			assertTrue(book.isBorrowed());
			verify(repository, times(1)).save(book);
		}

		@Test
		@DisplayName("대여중인 도서를 대여시 예외가 발생합니다")
		public void testBorrowAlreadyBorrowedBook() { // todo : parameterize Test 찾아보기
			String bookId = "1";
			BorrowBookRequestDto request = new BorrowBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			book.borrow();
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			assertThrows(BookException.class, () -> service.borrowBook(request), BOOK_ALREADY_BORROWED);
		}

		@Test
		@DisplayName("분실된 도서를 대여시 예외가 발생합니다")
		public void testBorrowLostBook() {
			String bookId = "1";
			BorrowBookRequestDto request = new BorrowBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			book.lost();
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			assertThrows(BookException.class, () -> service.borrowBook(request), BOOK_LOST);
		}

		@Test
		@DisplayName("정리중인 도서를 대여시 예외가 발생합니다")
		public void testBorrowBookUnderOrganizing() {
			String bookId = "1";
			BorrowBookRequestDto request = new BorrowBookRequestDto(bookId);
			Book book = new Book("title", "author", 100L);
			book.returned();
			when(repository.findById(request.getId())).thenReturn(Optional.of(book));

			assertThrows(BookException.class, () -> service.borrowBook(request), BOOK_UNDER_ORGANIZING);
		}

		@Test
		@DisplayName("존재하지 않는 도서를 대여시 예외가 발생합니다")
		public void testBorrowNonExistentBook() {
			String bookId = "1";
			BorrowBookRequestDto request = new BorrowBookRequestDto(bookId);
			when(repository.findById(request.getId())).thenReturn(Optional.empty());

			assertThrows(BookException.class, () -> service.borrowBook(request), BOOK_NOT_FOUND);
		}
	}
}