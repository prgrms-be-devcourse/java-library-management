package com.programmers.library_management.service;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.domain.StatusType;
import com.programmers.library_management.exception.*;
import com.programmers.library_management.repository.BookRepository;
import com.programmers.library_management.repository.TestBookRepository;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test LibraryManagementService when TestMode activate")

class TestLibraryManagementServiceTest {
    private LibraryManagementService libraryManagementService;
    private BookRepository bookRepository;
    private final String expectedBookTitle = "테스트 책";
    private final String expectedBookWriter = "작성자";
    private final int expectedBookPageNumber = 10;

    @BeforeEach
    void init(){
        bookRepository = new TestBookRepository();
        libraryManagementService = new LibraryManagementService(bookRepository);
    }

    @AfterEach
    void cleanup(){
        bookRepository.deleteAll();
    }

    @Test
    void testAddBookSuccess(){
        // Arrange & Act
        libraryManagementService.addBook(expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        // Assert
        Book actualResult = libraryManagementService.searchBook(expectedBookTitle).get(0);
        assertEquals(Book.newBookOf(0, expectedBookTitle, expectedBookWriter, expectedBookPageNumber),
                actualResult);
    }

    @Test
    void testAddBookFailure(){
        // Arrange
        bookRepository.save(Book.newBookOf(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber));
        // Act & Assert
        assertThrows(CBookAlreadyExistException.class, ()-> libraryManagementService.addBook(expectedBookTitle, expectedBookWriter, expectedBookPageNumber));
    }

    @Test
    void testRantBookSuccess(){
        // Arrange
        Book expectedResult = Book.newBookOf(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        bookRepository.save(expectedResult);
        // Act
        libraryManagementService.rantBook(expectedResult.getId());
        // Assert
        assertEquals(StatusType.Ranted, bookRepository.findById(expectedResult.getId()).get().getStatus());
    }


    @Test
    void testRantBookFailure(){
        // Arrange
        Book expectedResult = Book.newBookOf(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.rant();
        bookRepository.save(expectedResult);
        // Act & Assert
        assertThrows(CBookAlreadyRantedException.class, ()-> libraryManagementService.rantBook(expectedResult.getId()));
    }


    @Test
    void testReturnBookSuccess(){
        // Arrange
        Book expectedResult = Book.newBookOf(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.rant();
        bookRepository.save(expectedResult);
        // Act
        libraryManagementService.returnBook(expectedResult.getId());
        // Assert
        assertEquals(StatusType.Organized, libraryManagementService.showAllBooks().get(0).getStatus());
    }


    @Test
    void testReturnBookFailure(){
        // Arrange
        Book expectedResult = Book.newBookOf(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.returned();
        bookRepository.save(expectedResult);
        // Act & Assert
        assertThrows(CBookAlreadyReturnedException.class, ()-> libraryManagementService.returnBook(expectedResult.getId()));
    }


    @Test
    void testLostBookSuccess(){
        // Arrange
        Book expectedResult = Book.newBookOf(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        bookRepository.save(expectedResult);
        // Act
        libraryManagementService.lostBook(expectedResult.getId());
        // Assert
        assertEquals(StatusType.Lost, libraryManagementService.showAllBooks().get(0).getStatus());
    }

    @Test
    void testLostBookFailure(){
        // Arrange
        Book expectedResult = Book.newBookOf(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        expectedResult.lost();
        bookRepository.save(expectedResult);
        // Act & Assert
        assertThrows(CBookAlreadyLostException.class, ()-> libraryManagementService.lostBook(expectedResult.getId()));
    }

    @Test
    void testDeleteBookSuccess(){
        // Arrange
        Book expectedResult = Book.newBookOf(bookRepository.generateBookId(), expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        bookRepository.save(expectedResult);
        // Act
        libraryManagementService.deleteBook(expectedResult.getId());
        // Assert
        assertTrue(bookRepository.findById(0).isEmpty());
    }

    @Test
    void testDeleteBookFailure(){
        // Assert
        assertThrows(CBookIdNotExistException.class, ()->libraryManagementService.deleteBook(bookRepository.generateBookId()));
    }
}