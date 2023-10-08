package com.dev_course.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

abstract class BookManagerTest {
    protected abstract BookManager getBookManager();

    @Nested
    @DisplayName("초기 생성, 데이터 로드 테스트")
    class TestGetBooks {
        @Test
        @DisplayName("초기 생성 시 도서 리스트는 비어 있어야 한다.")
        void testGetBooksBeforeInit() {
            // given
            BookManager bookManager = getBookManager();

            // when
            List<Book> books = bookManager.getBooks();

            // then
            assertThat(books).isEmpty();
        }

        @Test
        @DisplayName("init() 메서드 호출 시 도서 리스트는 init 데이터가 반영돼야 한다.")
        void testGetBooksAfterInit() {
            // given
            BookManager bookManager = getBookManager();
            List<Book> initData = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();

            initData.add(new Book(1, "test1", "tester", 111, now));
            initData.add(new Book(2, "test2", "tester", 222, now));
            initData.add(new Book(3, "test3", "tester", 333, now));

            // when
            bookManager.init(initData);

            // then
            List<Book> books = bookManager.getBooks();

            assertThat(books).containsAll(initData);
        }
    }

    @Nested
    @DisplayName("도서 생성 테스트")
    class TestCreate {
        @Test
        @DisplayName("생성된 도서는 대여 가능 상태여야 한다")
        void testCreateAvailableState() {
            // given
            BookManager bookManager = getBookManager();

            // when
            bookManager.create("test1", "tester", 11);
            bookManager.create("test2", "tester", 22);

            // then
            List<Book> books = bookManager.getBooks();

            assertThat(books).allMatch(book -> book.getState().isRentable());
        }

        @Test
        @DisplayName("도서 생성 시 유일한 id의 도서가 추가돼야 한다")
        void testCreateWithUniqueId() {
            // given
            BookManager bookManager = getBookManager();

            // when
            bookManager.create("test1", "tester", 11);
            bookManager.create("test2", "tester", 22);

            // then
            List<Book> books = bookManager.getBooks();
            long idCount = books.stream().mapToInt(Book::getId).count();

            assertThat(idCount).isEqualTo(2);
        }

        @Test
        @DisplayName("데이터 로드 후 도서 생성 시 모든 도서는 유일한 id를 갖고 있어야 한다.")
        void testCreateWitUniqueIdAfterInit() {
            // given
            BookManager bookManager = getBookManager();
            List<Book> initData = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();

            initData.add(new Book(1, "test1", "tester", 111, now));
            initData.add(new Book(5, "test2", "tester", 222, now));
            initData.add(new Book(10, "test3", "tester", 333, now));

            // when
            bookManager.init(initData);

            bookManager.create("test4", "tester", 11);
            bookManager.create("test5", "tester", 22);

            // then
            List<Book> books = bookManager.getBooks();
            long idCount = books.stream().mapToInt(Book::getId).count();

            assertThat(idCount).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("도서 제목 검색 테스트")
    class TestFindBookByTitle {
        @Test
        @DisplayName("검색 시 주어진 단어를 포함하는 모든 도서를 반환해야 한다.")
        void testGetInfoByTitle() {
            // given
            BookManager bookManager = getBookManager();
            List<Book> initData = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();

            initData.add(new Book(1, "banabana", "tester", 111, now));
            initData.add(new Book(2, "bana", "tester", 222, now));
            initData.add(new Book(3, "anab", "tester", 333, now));
            initData.add(new Book(4, "banan", "tester", 444, now));
            initData.add(new Book(5, "banana", "tester", 555, now));

            // when
            bookManager.init(initData);
            List<Book> books = bookManager.getBooksByTitle("bana");

            // then
            List<Book> requiredData = initData.stream()
                    .filter(book -> book.getTitle().contains("bana"))
                    .toList();

            assertThat(books).isEqualTo(requiredData);
        }
    }

    @Nested
    @DisplayName("도서 대여 테스트")
    class TestRentById {
        @Test
        @DisplayName("도서 대여 시 해당 도서는 대여 중으로 상태가 변경돼야 한다.")
        void testRentByIdChangeState() {
            // given
            BookManager bookManager = getBookManager();
            List<Book> initData = new ArrayList<>();
            Book book = new Book(1, "test1", "tester", 111, LocalDateTime.now());

            initData.add(book);
            bookManager.init(initData);

            // when
            bookManager.rentById(1);

            // then
            assertThat(book.getState()).isEqualTo(BookState.LOAN);
        }
    }

    @Nested
    @DisplayName("도서 반납 테스트")
    class TestReturnById {
        @Test
        @DisplayName("도서 반납 시 해당 도서는 도서 정리 중으로 상태가 변경돼야 한다.")
        void testReturnByIdChangeState() {
            // given
            BookManager bookManager = getBookManager();
            LocalDateTime now = LocalDateTime.now();
            Book loanBook = new Book(1, "test1", "tester", 111, now);
            Book lostBook = new Book(2, "test2", "tester", 222, now);

            bookManager.init(List.of(loanBook, lostBook));
            loanBook.setState(BookState.LOAN);
            lostBook.setState(BookState.LOST);

            // when
            bookManager.returnById(loanBook.getId());
            bookManager.returnById(lostBook.getId());

            // then
            assertThat(loanBook.getState()).isEqualTo(BookState.PROCESSING);
            assertThat(lostBook.getState()).isEqualTo(BookState.PROCESSING);
        }
    }

    @Nested
    @DisplayName("도서 분실 테스트")
    class TestLossById {
        @Test
        @DisplayName("도서 분실 시 해당 도서는 분실로 상태가 변경돼야 한다.")
        void testLossByIdChangeState() {
            // given
            BookManager bookManager = getBookManager();
            LocalDateTime now = LocalDateTime.now();
            Book availableBook = new Book(1, "test1", "tester", 111, now);
            Book processingBook = new Book(2, "test2", "tester", 222, now);
            Book loanBook = new Book(3, "test3", "tester", 333, now);

            bookManager.init(List.of(availableBook, processingBook, loanBook));
            availableBook.setState(BookState.AVAILABLE);
            processingBook.setState(BookState.PROCESSING);
            loanBook.setState(BookState.LOAN);

            // when
            bookManager.lossById(availableBook.getId());
            bookManager.lossById(processingBook.getId());
            bookManager.lossById(loanBook.getId());

            // then
            assertThat(availableBook.getState()).isEqualTo(BookState.LOST);
            assertThat(processingBook.getState()).isEqualTo(BookState.LOST);
            assertThat(loanBook.getState()).isEqualTo(BookState.LOST);
        }
    }

    @Nested
    @DisplayName("도서 삭제 테스트")
    class TestDeleteById {
        @Test
        @DisplayName("상태에 상관없이 삭제 시 도서 리스트에서 삭제돼야 한다.")
        void testDeleteByIdChangeState() {
            // given
            BookManager bookManager = getBookManager();
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
            bookManager.deleteById(availableBook.getId());
            bookManager.deleteById(processingBook.getId());
            bookManager.deleteById(loanBook.getId());
            bookManager.deleteById(lostBook.getId());

            // then
            List<Book> books = bookManager.getBooks();

            assertThat(books).doesNotContainAnyElementsOf(initData);
        }
    }

    @Nested
    @DisplayName("도서 상태 업데이트 테스트")
    class TestUpdateStates {
        @Test
        @DisplayName("5분 이상 정리 중인 도서는 대여 가능으로 상태가 변경돼야 한다.")
        void testUpdateStatesChangeToAvailable() {
            // given
            BookManager bookManager = getBookManager();
            LocalDateTime processAt = LocalDateTime.now().minusMinutes(5);
            Book processedBook = new Book(1, "test1", "tester", 111, processAt);

            bookManager.init(List.of(processedBook));
            processedBook.setState(BookState.PROCESSING);

            // when
            bookManager.updateStates();

            // then
            assertThat(processedBook.getState()).isEqualTo(BookState.AVAILABLE);
        }

        @Test
        @DisplayName("5분 이상 정리한 도서가 아니면 상태가 변경되면 안된다.")
        void testUpdateStatesNotChange() {
            // given
            BookManager bookManager = getBookManager();
            LocalDateTime currentTime = LocalDateTime.now();
            Book processingBook = new Book(1, "test1", "tester", 111, currentTime);

            bookManager.init(List.of(processingBook));
            processingBook.setState(BookState.PROCESSING);

            // when
            bookManager.updateStates();

            // then
            assertThat(processingBook.getState()).isNotEqualTo(BookState.AVAILABLE);
        }
    }
}
