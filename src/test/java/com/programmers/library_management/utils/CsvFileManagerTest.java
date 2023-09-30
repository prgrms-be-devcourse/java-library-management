package com.programmers.library_management.utils;

import com.programmers.library_management.domain.Book;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test CsvFileManager")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CsvFileManagerTest {
    private CsvFileManager csvFileManager;

    @BeforeEach
    void init(){
        csvFileManager = new CsvFileManager("book_list_test");
    }

    @DisplayName("Test saveMemoryToCsv")
    @Order(1)
    @Test
    void testSaveMemoryToCsv(){
        // Arrange
        Map<Integer, Book> tempMemory = new HashMap<>();
        Book tempBook = Book.newBookOf(1, "test", "test", 10);
        tempMemory.put(tempBook.getId(), tempBook);
        // Act
        csvFileManager.saveMemoryToCsv(tempMemory);
    }

    @DisplayName("Test loadMemoryFromCsv")
    @Order(2)
    @Test
    void testLoadMemoryFromCsv(){
        // Arrange
        Map<Integer, Book> expectedResult = new HashMap<>();
        Book tempBook = Book.newBookOf(1, "test", "test", 10);
        expectedResult.put(tempBook.getId(), tempBook);
        // Act
        Map<Integer, Book> actualResult = csvFileManager.loadMemoryFromCsv();
        // Assert
        assertEquals(actualResult, expectedResult);
    }

}