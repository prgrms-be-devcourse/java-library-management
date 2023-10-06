package app.library.management.core.repository.file;

import app.library.management.core.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static app.library.management.core.domain.BookStatus.AVAILABLE;
import static app.library.management.core.repository.file.Mapper.BookToBookVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

class FileStorageAdaptorTest {

    private FileStorageAdaptor fileStorageAdaptor;
    private FileStorage fileStorageMock;

    @BeforeEach
    public void setUp() {
        fileStorageMock = mock(FileStorage.class);
        fileStorageAdaptor = new FileStorageAdaptor(fileStorageMock);
    }

    @DisplayName("도서를 추가하면 파일에 추가되고, id값이 할당된 책을 반환한다.")
    @Test
    void save() {
        // given
        Book book = new Book("책1", "작가1", 100);

        when(fileStorageMock.readFile()).thenReturn(Collections.emptyList());
        doNothing().when(fileStorageMock).saveFile(any(BookVO.class));

        // when
        Book savedBook = fileStorageAdaptor.save(book);

        // then
        verify(fileStorageMock, times(1)).saveFile(any(BookVO.class));
        assertThat(savedBook.getId()).isNotEqualTo(-1);
    }

    @DisplayName("도서 전체를 조회하면 'Book'을 리스트 형태로 반환한다.")
    @Test
    void findAll() {
        // given
        Book book = new Book("책1", "작가1", 100);
        BookVO bookVO = BookToBookVO(book);

        when(fileStorageMock.readFile()).thenReturn(List.of(bookVO));

        // when
        List<Book> bookList = fileStorageAdaptor.findAll();

        // then
        verify(fileStorageMock, times(1)).readFile();
        assertThat(bookList).contains(book);
    }

    @DisplayName("도서 제목으로 도서들을 조회할 수 있다.")
    @Test
    void findByTitle() {
        // given
        Book book1 = new Book("책1", "작가1", 100);
        Book book2 = new Book("책2", "작가2", 100);
        Book book3 = new Book("Book3", "작가3", 100);
        BookVO bookVO1 = BookToBookVO(book1);
        BookVO bookVO2 = BookToBookVO(book2);
        BookVO bookVO3 = BookToBookVO(book3);

        when(fileStorageMock.readFile()).thenReturn(List.of(bookVO1, bookVO2, bookVO3));

        // when
        List<Book> bookList = fileStorageAdaptor.findByTitle("책");

        // then
        verify(fileStorageMock, times(1)).readFile();
        assertAll(
                () -> assertThat(bookList).hasSize(2),
                () -> assertThat(bookList).containsAll(List.of(book1, book2))
        );
    }

    @DisplayName("id로 도서를 조회할 수 있다.")
    @Test
    void findById() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Book book1 = new Book(1L, "책1", "작가1", 100, AVAILABLE, now);
        Book book2 = new Book(2L, "책2", "작가2", 100, AVAILABLE, now);
        Book book3 = new Book(3L, "Book3", "작가3", 100, AVAILABLE, now);
        BookVO bookVO1 = BookToBookVO(book1);
        BookVO bookVO2 = BookToBookVO(book2);
        BookVO bookVO3 = BookToBookVO(book3);

        when(fileStorageMock.readFile()).thenReturn(List.of(bookVO1, bookVO2, bookVO3));

        // when
        Optional<Book> optionalBook = fileStorageAdaptor.findById(1L);

        // then
        verify(fileStorageMock, times(1)).readFile();
        assertAll(
                () -> assertThat(optionalBook).isNotEmpty(),
                () -> assertThat(optionalBook).contains(book1)
        );
    }

    @DisplayName("도서를 삭제할 수 있다.")
    @Test
    void delete() {
        // given
        Book book = new Book("책1", "작가1", 100);

        doNothing().when(fileStorageMock).deleteFile(any(BookVO.class));

        // when
        fileStorageAdaptor.delete(book);

        // then
        verify(fileStorageMock, times(1)).deleteFile(any(BookVO.class));
    }

    @DisplayName("도서를 갱신할 수 있다.")
    @Test
    void update() {
        // given
        Book book = new Book("책1", "작가1", 100);

        doNothing().when(fileStorageMock).updateFile(any(BookVO.class));

        // when
        fileStorageAdaptor.update(book);

        // then
        verify(fileStorageMock, times(1)).updateFile(any(BookVO.class));
    }
}