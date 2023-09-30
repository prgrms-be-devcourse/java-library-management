package com.programmers.library_management.service;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.domain.StatusType;
import com.programmers.library_management.exception.CBookAlreadyExistException;
import com.programmers.library_management.exception.CBookAlreadyLostException;
import com.programmers.library_management.exception.CBookAlreadyRantedException;
import com.programmers.library_management.exception.CBookAlreadyReturnedException;
import com.programmers.library_management.repository.ProductBookRepository;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test LibraryManagementService when ProductMode activate")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductLibraryManagementServiceTest {
    static private LibraryManagementService libraryManagementService;

    @BeforeAll
    static void init(){
        libraryManagementService = new LibraryManagementService(new ProductBookRepository());
    }

    @DisplayName("test addBook Success")
    @Order(1)
    @Test
    void testAddBookSuccess(){
        // Arrange
        String expectedBookTitle = "테스트 책";
        String expectedBookWriter = "작성자";
        int expectedBookPageNumber = 10;
        // Act
        libraryManagementService.addBook(expectedBookTitle, expectedBookWriter, expectedBookPageNumber);
        // Assert
        Book actualResult = libraryManagementService.searchBook(expectedBookTitle).get(0);
        assertEquals(Book.newBookOf(0, expectedBookTitle, expectedBookWriter, expectedBookPageNumber),
                actualResult);
    }

    @DisplayName("test addBock Failure")
    @Order(2)
    @Test
    void testAddBookFailure(){
        // Arrange
        String expectedBookTitle = "테스트 책";
        String expectedBookWriter = "작성자";
        int expectedBookPageNumber = 10;
        // Act & Assert
        assertThrows(CBookAlreadyExistException.class, ()-> libraryManagementService.addBook(expectedBookTitle, expectedBookWriter, expectedBookPageNumber));
    }

    @DisplayName("test rantBook Success")
    @Order(3)
    @Test
    void testRantBookSuccess(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act
        libraryManagementService.rantBook(expectedResult.getId());
        // Assert
        assertEquals(StatusType.Ranted, libraryManagementService.showAllBooks().get(0).getStatus());
    }

    @DisplayName("test rantBook Failure")
    @Order(4)
    @Test
    void testRantBookFailure(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act & Assert
        assertThrows(CBookAlreadyRantedException.class, ()-> libraryManagementService.rantBook(expectedResult.getId()));
    }

    @DisplayName("test returnBook Success")
    @Order(5)
    @Test
    void testReturnBookSuccess(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act
        libraryManagementService.returnBook(expectedResult.getId());
        // Assert
        assertEquals(StatusType.Organized, libraryManagementService.showAllBooks().get(0).getStatus());
    }

    @DisplayName("test returnBook Failure")
    @Order(6)
    @Test
    void testReturnBookFailure(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act & Assert
        assertThrows(CBookAlreadyReturnedException.class, ()-> libraryManagementService.returnBook(expectedResult.getId()));
    }

    @DisplayName("test lostBook Success")
    @Order(7)
    @Test
    void testLostBookSuccess(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act
        libraryManagementService.lostBook(expectedResult.getId());
        // Assert
        assertEquals(StatusType.Lost, libraryManagementService.showAllBooks().get(0).getStatus());
    }

    @DisplayName("test lostBook Failure")
    @Order(8)
    @Test
    void testLostBookFailure(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act & Assert
        assertThrows(CBookAlreadyLostException.class, ()-> libraryManagementService.lostBook(expectedResult.getId()));
    }

    @DisplayName("test deleteBook Success")
    @Order(9)
    @Test
    void testDeleteBookSuccess(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act
        libraryManagementService.deleteBook(expectedResult.getId());
        // Assert
        assertEquals(0, libraryManagementService.showAllBooks().size());
    }
}