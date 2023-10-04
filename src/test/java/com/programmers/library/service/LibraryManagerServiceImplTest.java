package com.programmers.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.programmers.library.dto.AddBookRequestDto;
import com.programmers.library.dto.BorrowBookRequestDto;
import com.programmers.library.dto.DeleteBookRequestDto;
import com.programmers.library.dto.FindBookRequestDto;
import com.programmers.library.dto.LostBookRequestDto;
import com.programmers.library.dto.ReturnBookRequestDto;
import com.programmers.library.entity.Book;
import com.programmers.library.entity.BookFixture;
import com.programmers.library.repository.BookRepository;

public class LibraryManagerServiceImplTest {

	private LibraryManagerServiceImpl service;
	private BookRepository mockedBookRepository;
	private Long testId;
	private Book book;

	@BeforeEach
	public void setUp() {
		testId = 1L;
		book = BookFixture.createSampleBook();
		mockedBookRepository = Mockito.mock(BookRepository.class);
		service = new LibraryManagerServiceImpl(mockedBookRepository);
	}

	@Test
	@DisplayName("도서를 등록합니다")
	public void testAddBook() {
		AddBookRequestDto request = new AddBookRequestDto("Test Title", "Test Author", "100");
		Book book = request.toEntity();

		when(mockedBookRepository.save(any(Book.class))).thenReturn(book);

		service.addBook(request);

		verify(mockedBookRepository, times(1)).save(any(Book.class));
	}

	@Test
	@DisplayName("전체 도서를 조회합니다")
	public void testGetAllBooks() {
		List<Book> books = Arrays.asList(BookFixture.createJavaBook(), BookFixture.createPythonBook());

		when(mockedBookRepository.findAll()).thenReturn(books);

		List<Book> returnedBooks = service.getAllBooks();

		assertEquals(2, returnedBooks.size());
		verify(mockedBookRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("제목으로 도서를 조회합니다")
	public void testFindBooksByTitle() {
		Book book1 = BookFixture.createJavaBook();
		List<Book> books = List.of(book1);

		when(mockedBookRepository.findByTitle(book1.getTitle())).thenReturn(books);

		FindBookRequestDto request = new FindBookRequestDto(book1.getTitle());
		List<Book> foundBooks = service.findBooksByTitle(request);

		assertEquals(1, foundBooks.size());
		assertEquals(book1.getTitle(), foundBooks.get(0).getTitle());
		verify(mockedBookRepository, times(1)).findByTitle(book1.getTitle());
	}

	@Test
	@DisplayName("도서를 대여합니다")
	public void testBorrowBook() {
		when(mockedBookRepository.findById(testId)).thenReturn(Optional.of(book));

		BorrowBookRequestDto request = new BorrowBookRequestDto(testId.toString());
		service.borrowBook(request);

		verify(mockedBookRepository, times(1)).save(book);
	}

	@Test
	@DisplayName("도서를 반납합니다")
	public void testReturnBook() {
		when(mockedBookRepository.findById(testId)).thenReturn(Optional.of(book));
		ReturnBookRequestDto request = new ReturnBookRequestDto(testId.toString());
		book.borrow();

		service.returnBook(request);

		verify(mockedBookRepository, times(1)).save(book);
	}

	@Test
	@DisplayName("도서를 분실 처리합니다")
	public void testLostBook() {
		when(mockedBookRepository.findById(testId)).thenReturn(Optional.of(book));

		LostBookRequestDto request = new LostBookRequestDto(testId.toString());
		service.lostBook(request);

		verify(mockedBookRepository, times(1)).save(book);
	}

	@Test
	@DisplayName("도서를 삭제합니다")
	public void testDeleteBook() {
		when(mockedBookRepository.findById(testId)).thenReturn(Optional.of(book));

		DeleteBookRequestDto request = new DeleteBookRequestDto(testId.toString());
		service.deleteBook(request);

		verify(mockedBookRepository, times(1)).deleteById(book.getId());
	}

}
