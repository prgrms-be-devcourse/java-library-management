package com.programmers.library_management.service;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.domain.Status;
import com.programmers.library_management.exception.*;
import com.programmers.library_management.repository.TestBookRepository;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test LibraryManagementService when TestMode activate")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestLibraryManagementServiceTest {
    static private LibraryManagementService libraryManagementService;

    @BeforeAll
    static void init(){
        libraryManagementService = new LibraryManagementService(new TestBookRepository());
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
        assertEquals(new Book(0, expectedBookTitle, expectedBookWriter, expectedBookPageNumber),
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
        libraryManagementService.rantBook(expectedResult.getBookNumber());
        // Assert
        assertEquals(Status.Ranted, libraryManagementService.showAllBooks().get(0).getStatus());
    }

    @DisplayName("test rantBook Failure")
    @Order(4)
    @Test
    void testRantBookFailure(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act & Assert
        assertThrows(CBookAlreadyRantedException.class, ()-> libraryManagementService.rantBook(expectedResult.getBookNumber()));
    }

    @DisplayName("test returnBook Success")
    @Order(5)
    @Test
    void testReturnBookSuccess(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act
        libraryManagementService.returnBook(expectedResult.getBookNumber());
        // Assert
        assertEquals(Status.Organized, libraryManagementService.showAllBooks().get(0).getStatus());
    }

    @DisplayName("test returnBook Failure")
    @Order(6)
    @Test
    void testReturnBookFailure(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act & Assert
        assertThrows(CBookAlreadyReturnedException.class, ()-> libraryManagementService.returnBook(expectedResult.getBookNumber()));
    }

    @DisplayName("test lostBook Success")
    @Order(7)
    @Test
    void testLostBookSuccess(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act
        libraryManagementService.lostBook(expectedResult.getBookNumber());
        // Assert
        assertEquals(Status.Lost, libraryManagementService.showAllBooks().get(0).getStatus());
    }

    @DisplayName("test lostBook Failure")
    @Order(8)
    @Test
    void testLostBookFailure(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act & Assert
        assertThrows(CBookAlreadyLostException.class, ()-> libraryManagementService.lostBook(expectedResult.getBookNumber()));
    }

    @DisplayName("test deleteBook Success")
    @Order(9)
    @Test
    void testDeleteBookSuccess(){
        // Arrange
        Book expectedResult = libraryManagementService.showAllBooks().get(0);
        // Act
        libraryManagementService.deleteBook(expectedResult.getBookNumber());
        // Assert
        assertEquals(0, libraryManagementService.showAllBooks().size());
    }

}