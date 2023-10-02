package com.programmers.library_management.service;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.domain.StatusType;
import com.programmers.library_management.exception.*;
import com.programmers.library_management.repository.BookRepository;
import com.programmers.library_management.repository.ProductBookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test LibraryManagementService when ProductMode activate")
class ProductLibraryManagementServiceTest {
    private LibraryManagementService libraryManagementService;
    private BookRepository bookRepository;

    private final String expectedBookTitle = "테스트 책";
    private final String expectedBookWriter = "작성자";
    private final int expectedBookPageNumber = 10;

    @BeforeEach
    void init() {
        bookRepository = new ProductBookRepository();
        libraryManagementService = new LibraryManagementService(bookRepository);
    }

    @AfterEach
    void cleanup() {
        bookRepository.deleteAll();
    }

    @Test
    void testAddBookSuccess() {
        // Arrange & Act
        libraryManagementService.addBook(expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        // Assert
        Book actualResult = libraryManagementService.searchBook(expectedBookTitle).get(0);
        assertEquals(Book.of(0, expectedBookTitle, expectedBookWriter, expectedBookPageNumber),
                actualResult);
    }

    @Test
    void testAddBookFailure() {
        // Arrange
        bookRepository.save(Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber));
        // Act & Assert
        assertThrows(CBookAlreadyExistException.class, () -> libraryManagementService.addBook(expectedBookTitle, expectedBookWriter, expectedBookPageNumber));
    }

    @Test
    void testRantBookSuccess() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        bookRepository.save(expectedResult);
        // Act
        libraryManagementService.rantBook(expectedResult.getId());
        // Assert
        assertEquals(StatusType.Ranted, expectedResult.getStatus());
    }

    @Test
    void testRantBookFailureWhenThereIsNoBookInMemory() {
        // Act & Assert
        assertThrows(CBookIdNotExistException.class, () -> libraryManagementService.rantBook(Integer.MAX_VALUE));
    }

    @Test
    void testRantBookFailureWhenBookAlreadyRanted() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.rant();
        bookRepository.save(expectedResult);
        // Act & Assert
        assertThrows(CBookAlreadyRantedException.class, () -> libraryManagementService.rantBook(expectedResult.getId()));
    }

    @Test
    void testRantBookFailureWhenBookAlreadyLost() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.lost();
        bookRepository.save(expectedResult);
        // Act & Assert
        assertThrows(CBookAlreadyLostException.class, () -> libraryManagementService.rantBook(expectedResult.getId()));
    }

    @Test
    void testRantBookFailureWhenBookInOrganize() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.returned();
        bookRepository.save(expectedResult);
        // Act & Assert
        assertThrows(CBookInOrganizeException.class, () -> libraryManagementService.rantBook(expectedResult.getId()));
    }

    @Test
    void testReturnBookSuccess() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.rant();
        bookRepository.save(expectedResult);
        // Act
        libraryManagementService.returnBook(expectedResult.getId());
        // Assert
        assertEquals(StatusType.Organize, expectedResult.getStatus());
    }

    @Test
    void testReturnBookFailureWhenThereIsNoBookInMemory() {
        // Act & Assert
        assertThrows(CBookIdNotExistException.class, () -> libraryManagementService.returnBook(Integer.MAX_VALUE));
    }

    @Test
    void testReturnBookFailureWhenBookAvailable() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        bookRepository.save(expectedResult);
        // Act & Assert
        assertThrows(CBookAlreadyReturnedException.class, () -> libraryManagementService.returnBook(expectedResult.getId()));
    }

    @Test
    void testReturnBookFailureWhenBookAlreadyReturned() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.returned();
        bookRepository.save(expectedResult);
        // Act & Assert
        assertThrows(CBookAlreadyReturnedException.class, () -> libraryManagementService.returnBook(expectedResult.getId()));
    }

    @Test
    void testLostBookSuccess() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        bookRepository.save(expectedResult);
        // Act
        libraryManagementService.lostBook(expectedResult.getId());
        // Assert
        assertEquals(StatusType.Lost, expectedResult.getStatus());
    }

    @Test
    void testLostBookFailureWhenThereIsNoBookInMemory() {
        // Act & Assert
        assertThrows(CBookIdNotExistException.class, () -> libraryManagementService.lostBook(Integer.MAX_VALUE));
    }

    @Test
    void testLostBookFailureWhenBookAlreadyLost() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.lost();
        bookRepository.save(expectedResult);
        // Act & Assert
        assertThrows(CBookAlreadyLostException.class, () -> libraryManagementService.lostBook(expectedResult.getId()));
    }

    @Test
    void testDeleteBookSuccess() {
        // Arrange
        Book expectedResult = Book.of(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        bookRepository.save(expectedResult);
        // Act
        libraryManagementService.deleteBook(expectedResult.getId());
        // Assert
        assertTrue(bookRepository.findById(0).isEmpty());
    }

    @Test
    void testDeleteBookFailureWhenThereIsNoBookInMemory() {
        // Assert
        assertThrows(CBookIdNotExistException.class, () -> libraryManagementService.deleteBook(bookRepository.generateBookId()));
    }
}