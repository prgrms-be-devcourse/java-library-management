package app.library.management.core.service;

import app.library.management.core.domain.Book;
import app.library.management.core.domain.BookStatus;
import app.library.management.core.service.response.dto.status.Stage;
import app.library.management.infra.port.dto.request.BookRequestDto;
import app.library.management.infra.port.dto.response.BookResponseDto;
import app.library.management.core.domain.util.FileStatusManager;
import app.library.management.core.repository.BookRepository;
import app.library.management.core.service.response.dto.BookServiceResponse;
import app.library.management.core.service.response.dto.status.ResponseState;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {

    private final BookRepository bookRepository;
    private final FileStatusManager fileStatusManager;

    public BookService(BookRepository bookRepository, FileStatusManager fileStatusManager) {
        this.bookRepository = bookRepository;
        this.fileStatusManager = fileStatusManager;
    }

    /**
     * 도서 등록
     *
     * @param bookRequestDto
     */
    public void register(BookRequestDto bookRequestDto) {
        Book book = new Book(bookRequestDto.getTitle(), bookRequestDto.getAuthor(), bookRequestDto.getPages());
        bookRepository.save(book);
    }

    /**
     * 도서 목록 전체 조회
     *
     * @return List<BookResponseDto>
     */
    public List<BookResponseDto> findAll() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream()
                .map(it -> new BookResponseDto(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * 제목으로 도서 검색
     *
     * @param title
     * @return List<BookResponseDto>
     */
    public List<BookResponseDto> findAllByTitle(String title) {
        List<Book> bookList = bookRepository.findByTitle(title);
        return bookList.stream()
                .map(it -> new BookResponseDto(it.getId(), it.getTitle(), it.getAuthor(), it.getPages(), it.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * id 로 도서 대여
     *
     * @param id
     * @return BookServiceResponse
     */
    public BookServiceResponse rent(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            // 없는 자원에 대한 예외처리
            return new BookServiceResponse(ResponseState.NOTFOUND_EXCEPTION, Stage.RENT, null);
        }

        Book book = optionalBook.get();
        // [대여중, 정리중, 분실] 된 경우에 대한 예외처리
        if (book.getStatus() != BookStatus.AVAILABLE) {
            return new BookServiceResponse(ResponseState.VALIDATION_EXCEPTION, Stage.RENT, book.getStatus());
        }
        book.rent();
        bookRepository.update(book);
        return new BookServiceResponse(ResponseState.SUCCESS, Stage.RENT, book.getStatus());
    }

    /**
     * id 로 도서 반납
     *
     * @param id
     * @return BookServiceResponse
     */
    public BookServiceResponse returnBook(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            // 없는 자원에 대한 예외처리
            return new BookServiceResponse(ResponseState.NOTFOUND_EXCEPTION, Stage.RETURN, null);
        }
        Book book = optionalBook.get();
        if (!book.isBookReturnable()) {
            // 이미 반납된 도서 [정리중, 대여중] 에 대한 예외처리
            return new BookServiceResponse(ResponseState.VALIDATION_EXCEPTION, Stage.RETURN, book.getStatus());
        }
        book.returnBook();
        bookRepository.update(book);
        fileStatusManager.execute(book);
        return new BookServiceResponse(ResponseState.SUCCESS, Stage.RETURN, book.getStatus());
    }

    /**
     * id 로 도서 분실 처리
     *
     * @param id
     * @return BookServiceResponse
     */
    public BookServiceResponse reportLost(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            // 없는 자원에 대한 예외처리
            return new BookServiceResponse(ResponseState.NOTFOUND_EXCEPTION, Stage.LOST, null);
        }
        Book book = optionalBook.get();
        if (book.getStatus() == BookStatus.LOST) {
            // 이미 [분실]된 도서에 대한 예외처리
            return new BookServiceResponse(ResponseState.VALIDATION_EXCEPTION, Stage.LOST, book.getStatus());
        }
        book.lost();
        bookRepository.update(book);
        return new BookServiceResponse(ResponseState.SUCCESS, Stage.LOST, book.getStatus());
    }

    /**
     * id 로 도서 삭제
     *
     * @param id
     * @return BookServiceResponse
     */
    public BookServiceResponse delete(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            // 없는 자원에 대한 예외처리
            return new BookServiceResponse(ResponseState.NOTFOUND_EXCEPTION, Stage.DELETE, null);
        }
        bookRepository.delete(optionalBook.get());
        return new BookServiceResponse(ResponseState.SUCCESS, Stage.DELETE, null);
    }
}
