package com.dev_course.library;

import com.dev_course.book.Book;
import com.dev_course.book.BookManager;
import com.dev_course.book.BookState;
import com.dev_course.book.ListBookManager;
import com.dev_course.data_module.DataManager;
import com.dev_course.data_module.EmptyDataManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.dev_course.library.LibraryMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class LibraryServiceTest {
    private LibraryService getLibraryService() {
        return new LibraryService(getDataManager(), getBookManager());
    }

    private LibraryService getLibraryService(BookManager bookManager) {
        return new LibraryService(getDataManager(), bookManager);
    }

    private DataManager<Book> getDataManager() {
        return new EmptyDataManager<>();
    }

    private BookManager getBookManager() {
        return new ListBookManager();
    }

    @Nested
    @DisplayName("도서 생성 테스트")
    class TestCreateBook {
        @Test
        @DisplayName("도서 생성에 성공하면 성공 메시지를 반환해야 한다.")
        void testCreateSuccess() {
            // given
            LibraryService libraryService = getLibraryService();

            // when
            String msg = libraryService.createBook("test1", "tester", 11);

            // then
            assertThat(msg).isEqualTo(SUCCESS_CREATE_BOOK.msg());
        }

        @Test
        @DisplayName("이미 추가된 제목의 도서 생성 시 실패 메시지를 반환해야 한다.")
        void testCreateFailExistTitle() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);

            bookManager.create("test1", "tester", 11);


            // when
            String msg = libraryService.createBook("test1", "tester", 22);

            // then
            assertThat(msg).isEqualTo(ALREADY_EXIST_TITLE.msg());
        }
    }

    @Nested
    @DisplayName("도서 목록 조회 테스트")
    class TestBookInfos {
        @Test
        @DisplayName("도서 목록 조회 시 요구된 형식을 동일해야 한다.")
        void testBookInfos() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            List<Book> initData = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();

            initData.add(new Book(1, "test1", "tester", 111, now));
            initData.add(new Book(5, "test2", "tester", 222, now));
            initData.add(new Book(10, "test3", "tester", 333, now));


            // when
            bookManager.init(initData);
            String infos = libraryService.bookInfos();

            // then
            String requiredFormat = initData.stream()
                    .map(Book::info)
                    .collect(Collectors.joining("\n------------------------------\n"));

            assertThat(infos).isEqualTo(requiredFormat);
        }
    }

    @Nested
    @DisplayName("도서 제목 검색 테스트")
    class TestFindBookByTitle {
        @Test
        @DisplayName("검색 시 주어진 단어를 포함하는 모든 도서를 반환해야 한다.")
        void testFindBookByTitle() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            List<Book> initData = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();

            initData.add(new Book(1, "banabana", "tester", 111, now));
            initData.add(new Book(2, "bana", "tester", 222, now));
            initData.add(new Book(3, "anab", "tester", 333, now));
            initData.add(new Book(4, "banan", "tester", 444, now));
            initData.add(new Book(5, "banana", "tester", 555, now));

            // when
            bookManager.init(initData);
            String infoByTitle = libraryService.findBooksByTitle("bana");

            // then
            String requiredData = initData.stream()
                    .filter(book -> book.getTitle().contains("bana"))
                    .map(Book::info)
                    .collect(Collectors.joining("\n------------------------------\n"));

            assertThat(infoByTitle).isEqualTo(requiredData);
        }
    }

    @Nested
    @DisplayName("도서 대여 테스트")
    class TestRentBookById {
        @Test
        @DisplayName("도서 대여 시 성공 메시지를 반환해야 한다.")
        void testRentByIdSuccess() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            List<Book> initData = new ArrayList<>();

            initData.add(new Book(1, "test1", "tester", 111, LocalDateTime.now()));
            bookManager.init(initData);

            // when
            String msg = libraryService.rentBookById(1);

            // then
            assertThat(msg).isEqualTo(SUCCESS_RENT_BOOK.msg());
        }

        @Test
        @DisplayName("등록되지 않은 id의 도서 대여 시 실패 메시지를 반환해야 한다.")
        void testRentByIdFailNotExistId() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            Book book = new Book(1, "test1", "tester", 11, LocalDateTime.now());

            bookManager.init(List.of(book));

            // when
            Throwable thrown = catchThrowable(() -> libraryService.rentBookById(book.getId() + 1));

            // then
            assertThat(thrown).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("대여할 수 없는 도서를 대여할 때 실패 메시지를 반환해야 한다.")
        void testRentByIdFailNonAvailable() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            List<Book> initData = new ArrayList<>();
            Book book = new Book(1, "test1", "tester", 111, LocalDateTime.now());

            initData.add(book);
            bookManager.init(initData);

            // when
            List<String> msgList = new ArrayList<>();

            book.setState(BookState.LOAN);
            msgList.add(libraryService.rentBookById(1));

            book.setState(BookState.LOST);
            msgList.add(libraryService.rentBookById(1));

            book.setState(BookState.PROCESSING);
            msgList.add(libraryService.rentBookById(1));

            // then
            assertThat(msgList).allMatch(msg -> msg.equals(FAIL_RENT_BOOK.msg()));
        }
    }

    @Nested
    @DisplayName("도서 반납 테스트")
    class TestReturnBookById {
        @Test
        @DisplayName("도서 반납 시 성공 메시지를 반환해야 한다.")
        void testReturnByIdSuccess() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            LocalDateTime now = LocalDateTime.now();
            Book loanBook = new Book(1, "test1", "tester", 111, now);
            Book lostBook = new Book(2, "test2", "tester", 222, now);

            bookManager.init(List.of(loanBook, lostBook));
            loanBook.setState(BookState.LOAN);
            lostBook.setState(BookState.LOST);

            // when
            String loanReturnMsg = libraryService.returnBookById(loanBook.getId());
            String lostReturnMsg = libraryService.returnBookById(lostBook.getId());

            // then
            assertThat(loanReturnMsg).isEqualTo(SUCCESS_RETURN_BOOK.msg());
            assertThat(lostReturnMsg).isEqualTo(SUCCESS_RETURN_BOOK.msg());
        }

        @Test
        @DisplayName("등록되지 않은 id의 도서 반납 시 실패 메시지를 반환해야 한다.")
        void testReturnByIdFailNotExistId() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            Book book = new Book(1, "test1", "tester", 11, LocalDateTime.now());

            bookManager.init(List.of(book));
            book.setState(BookState.LOAN);

            // when
            Throwable thrown = catchThrowable(() -> libraryService.returnBookById(book.getId() + 1));

            // then
            assertThat(thrown).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("반납할 수 없는 도서를 반납할 때 실패 메시지를 반환해야 한다.")
        void testReturnByIdFailNonReturnable() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            Book book = new Book(1, "test1", "tester", 111, LocalDateTime.now());

            bookManager.init(List.of(book));

            // when
            book.setState(BookState.AVAILABLE);
            String availableReturnMsg = libraryService.returnBookById(book.getId());

            book.setState(BookState.PROCESSING);
            String processingReturnMsg = libraryService.returnBookById(book.getId());

            // then
            assertThat(availableReturnMsg).isEqualTo(FAIL_RETURN_BOOK.msg());
            assertThat(processingReturnMsg).isEqualTo(FAIL_RETURN_BOOK.msg());
        }
    }

    @Nested
    @DisplayName("도서 분실 테스트")
    class TestLossById {
        @Test
        @DisplayName("도서 분실 시 성공 메시지를 반환해야 한다.")
        void testLossByIdSuccess() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            LocalDateTime now = LocalDateTime.now();
            Book availableBook = new Book(1, "test1", "tester", 111, now);
            Book processingBook = new Book(2, "test2", "tester", 222, now);
            Book loanBook = new Book(3, "test3", "tester", 333, now);

            bookManager.init(List.of(availableBook, processingBook, loanBook));
            availableBook.setState(BookState.AVAILABLE);
            processingBook.setState(BookState.PROCESSING);
            loanBook.setState(BookState.LOAN);

            // when
            String availableLossMsg = libraryService.lossBookById(availableBook.getId());
            String processingLossMsg = libraryService.lossBookById(processingBook.getId());
            String loanLossMsg = libraryService.lossBookById(loanBook.getId());

            // then
            assertThat(availableLossMsg).isEqualTo(SUCCESS_LOSS_BOOK.msg());
            assertThat(processingLossMsg).isEqualTo(SUCCESS_LOSS_BOOK.msg());
            assertThat(loanLossMsg).isEqualTo(SUCCESS_LOSS_BOOK.msg());
        }

        @Test
        @DisplayName("등록되지 않은 id의 도서 분실 시 실패 메시지를 반환해야 한다.")
        void testLossByIdFailNotExistId() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            Book book = new Book(1, "test1", "tester", 11, LocalDateTime.now());

            bookManager.init(List.of(book));

            // when
            Throwable thrown = catchThrowable(() -> libraryService.lossBookById(book.getId() + 1));

            // then
            assertThat(thrown).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("이미 분실된 도서를 분실할 때 실패 메시지를 반환해야 한다.")
        void testLossByIdFailAlreadyLost() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            Book lostBook = new Book(1, "test1", "tester", 111, LocalDateTime.now());

            bookManager.init(List.of(lostBook));
            lostBook.setState(BookState.LOST);

            // when
            String msg = libraryService.lossBookById(lostBook.getId());

            // then
            assertThat(msg).isEqualTo(ALREADY_LOST_BOOK.msg());
        }
    }

    @Nested
    @DisplayName("도서 삭제 테스트")
    class TestDeleteBookById {
        @Test
        @DisplayName("도서 삭제 시 성공 메시지를 반환해야 한다.")
        void testDeleteByIdSuccess() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            LocalDateTime now = LocalDateTime.now();
            Book availableBook = new Book(1, "test1", "tester", 111, now);
            Book processingBook = new Book(2, "test2", "tester", 222, now);
            Book loanBook = new Book(3, "test3", "tester", 333, now);
            Book lostBook = new Book(4, "test4", "tester", 444, now);

            List<Book> initData = List.of(availableBook, processingBook, loanBook, lostBook);

            bookManager.init(initData);

            availableBook.setState(BookState.AVAILABLE);
            processingBook.setState(BookState.PROCESSING);
            loanBook.setState(BookState.LOAN);
            lostBook.setState(BookState.LOST);

            // when
            List<String> msgList = initData.stream()
                    .map(book -> libraryService.deleteBookById(book.getId()))
                    .toList();

            // then
            assertThat(msgList).allMatch(SUCCESS_DELETE_BOOK.msg()::equals);
        }

        @Test
        @DisplayName("등록되지 않은 id의 도서 삭제 시 실패 메시지를 반환해야 한다.")
        void testDeleteByIdFailNotExistId() {
            // given
            BookManager bookManager = getBookManager();
            LibraryService libraryService = getLibraryService(bookManager);
            Book book = new Book(1, "test1", "tester", 11, LocalDateTime.now());

            bookManager.init(List.of(book));

            // when
            String msg = libraryService.deleteBookById(book.getId() + 1);

            // then
            assertThat(msg).isEqualTo(NOT_EXIST_ID.msg());
        }
    }
}
