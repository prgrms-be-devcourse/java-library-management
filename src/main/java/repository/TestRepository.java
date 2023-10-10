package repository;

import domain.Book;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static domain.BookCondition.*;

public class TestRepository implements Repository {

    @Override
    public void load(List<Book> bookList) {
        bookList.clear();
    }

    @Override
    public void save(int id, String title, String author, int page, List<Book> bookList) {
        bookList.add(new Book(id, title, author, page, AVAILABLE.getCondition()));
    }

    @Override
    public List<Book> findByTitleContaining(String searchTitle, List<Book> bookList) {
        return bookList.stream()
                .filter(book -> book.isTitleContaining(book, searchTitle))
                .map(book -> new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPage(), book.getCondition()))
                .toList();
    }

    @Override
    public String rentById(int rentId, List<Book> bookList) {
        String message = "";
        boolean isBookExist = false;
        Iterator<Book> bookIterator = bookList.iterator();

        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getId() == rentId) {
                isBookExist = true;
                message = checkRent(book);
                break; // ID를 찾았으므로 루프 종료
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        return message;
    }

    @Override
    public String returnById(int returnId, List<Book> bookList) {
        String message = "";
        boolean isBookExist = false;
        Iterator<Book> bookIterator = bookList.iterator();

        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getId() == returnId) {
                isBookExist = true;
                message = checkReturn(book);
                break;
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        return message;
    }

    @Override
    public String lostById(int lostId, List<Book> bookList) {
        String message = "";
        boolean isBookExist = false;

        Iterator<Book> bookIterator = bookList.iterator();

        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getId() == lostId) {
                isBookExist = true;
                message = checkLost(book);
                break;
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        return message;
    }

    @Override
    public String deleteById(int deleteId, List<Book> bookList) {
        String message = "";
        boolean isBookExist = false;

        Iterator<Book> bookIterator = bookList.iterator();
        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getId() == deleteId) {
                bookIterator.remove(); // delete Id를 가진 레코드를 삭제 -> enhanced for vs bookIterator 결정 이유
                isBookExist = true;
                message = "도서가 삭제 처리 되었습니다.";
                break;
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        return message;
    }

    //대여 가능한 도서인지 확인하는 메서드
    private static String checkRent(Book book) {
        String message;
        if(Objects.equals(book.getCondition(), AVAILABLE.getCondition())){
            book.setCondition(RENTED.getCondition());
            message = "도서가 대여 처리 되었습니다.";
        }
        else if (Objects.equals(book.getCondition(), RENTED.getCondition())) {
            message = "이미 대여중인 도서입니다.";
        }
        else message = "현재 대여가 불가능한 도서입니다.";
        return message;
    }

    //반납 가능한 도서인지 확인하는 메서드
    private static String checkReturn(Book book) {
        String message;
        if(Objects.equals(book.getCondition(), RENTED.getCondition()) || Objects.equals(book.getCondition(), LOST.getCondition())) { //대여 중 or 분실됨이면 반납 가능
            book.setCondition(ORGANIZING.getCondition());
            message = "도서가 반납 처리 되었습니다";
        } else { // 대여 가능
            message = "원래 대여가 가능한 도서입니다.";
        }
        return message;
    }

    //분실 처리 도서인지 확인하는 메서드
    private static String checkLost(Book book) {
        String message;
        if(Objects.equals(book.getCondition(), LOST.getCondition())) {
            message = "이미 분실 처리된 도서입니다.";
        } else {
            book.setCondition(LOST.getCondition());
            message = "도서가 분실 처리 되었습니다.";
        }
        return message;
    }
}
